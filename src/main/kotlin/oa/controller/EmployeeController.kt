package oa.controller

import oa.common.JsonResponse
import oa.common.JsonResponse.Companion.success
import oa.entity.Employee
import oa.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/employees")
class EmployeeController @Autowired constructor(private val service: EmployeeService) {
    @GetMapping("")
    @PreAuthorize("hasAuthority('oa:employee:list')")
    fun getList(@RequestParam page: Int, @RequestParam size: Int): JsonResponse<List<Employee>> {
        require(page >= 1)
        val employees = if (size < 0) {
            // Return all employees without pagination
            service.findAll()
        } else {
            // Return employees with pagination
            service.findAll(PageRequest.of(page - 1, size))
        }
        return success(employees)
    }

    @GetMapping("/{jobNumber}")
    @PreAuthorize("hasAuthority('oa:employee:view') or hasAuthority('oa:employee:view-current')")
    fun getByJobNumber(
        @PathVariable jobNumber: String,
        @RequestParam(required = false, defaultValue = "false") current: Boolean
    ): JsonResponse<Employee> {
        val newJobNumber = if (current) SecurityContextHolder.getContext().authentication.name else jobNumber
        return success(service.findByJobNumber(newJobNumber))
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('oa:employee:modify')")
    fun save(@RequestBody employee: Employee): JsonResponse<Employee> =
        success(service.save(employee))


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('oa:employee:modify')")
    fun update(@PathVariable id: Long, @RequestBody employee: Employee): JsonResponse<Employee> {
        // 为了满足restful api
        // 校验id和实际的员工id值是否相等
        require(id == employee.id)
        return success(service.update(employee))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('oa:employee:delete')")
    fun delete(@PathVariable id: Long): JsonResponse<Void> {
        service.deleteById(id)
        return success()
    }

    @DeleteMapping("/:deleteBatch")
    @PreAuthorize("hasAuthority('oa:employee:delete')")
    fun deleteBatch(@RequestBody jobNumberList: List<String>): JsonResponse<Void> {
        service.deleteBatch(jobNumberList)
        return success()
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('oa:employee:list')")
    fun count(): JsonResponse<Long> = success(service.count())
}