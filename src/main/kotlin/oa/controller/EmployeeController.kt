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
class EmployeeController @Autowired constructor(private val employeeService: EmployeeService) {
    @GetMapping("")
    @PreAuthorize("hasAuthority('oa:employee:list')")
    fun getList(@RequestParam page: Int, @RequestParam size: Int): JsonResponse<List<Employee>> {
        val sizeMax = 20
        require(size in 1..sizeMax && page >= 1)
        val employees = employeeService.findAll(PageRequest.of(page - 1, size))
        return success(employees.stream().toList())
    }

    @GetMapping("/{jobNumber}")
    @PreAuthorize("hasAuthority('oa:employee:view') or hasAuthority('oa:employee:view-current')")
    fun getByJobNumber(
        @PathVariable jobNumber: String,
        @RequestParam(required = false, defaultValue = "false") current: Boolean
    ): JsonResponse<Employee> {
        val newJobNumber = if (current) SecurityContextHolder.getContext().authentication.name else jobNumber
        return success(employeeService.findByJobNumber(newJobNumber))
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('oa:employee:modify')")
    fun save(@RequestBody employee: Employee): JsonResponse<Employee> =
        success(employeeService.save(employee))


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('oa:employee:modify')")
    fun update(@PathVariable id: Long, @RequestBody employee: Employee): JsonResponse<Employee> {
        // 为了满足restful api
        // 校验id和实际的员工id值是否相等
        require(id == employee.id)
        return success(employeeService.update(employee))
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('oa:employee:delete')")
    fun delete(@PathVariable id: Long): JsonResponse<Void> {
        employeeService.deleteById(id)
        return success()
    }

    @PostMapping("/:deleteBatch")
    @PreAuthorize("hasAuthority('oa:employee:delete')")
    fun deleteBatch(@RequestBody jobNumberList: List<String>): JsonResponse<Void> {
        employeeService.deleteBatch(jobNumberList)
        return success()
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('oa:employee:list')")
    fun count(): JsonResponse<Long> = success(employeeService.count())
}