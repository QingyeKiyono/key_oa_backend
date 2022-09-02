package com.key.oa.controller;

import com.key.oa.common.JsonResponse;
import com.key.oa.entity.Employee;
import com.key.oa.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 和员工相关的操作
 *
 * @author 孙强
 */
@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('oa:employee:list')")
    public JsonResponse<List<Employee>> getEmployeeList(@RequestParam int page, @RequestParam int size) {
        int sizeMax = 20;

        if (size > sizeMax || page < 1) {
            throw new IllegalArgumentException();
        }

        Page<Employee> employees = this.employeeService.findAll(PageRequest.of(page - 1, size));
        return JsonResponse.success(employees.stream().toList());
    }

    @GetMapping("/{jobNumber}")
    @PreAuthorize("hasAuthority('oa:employee:view') or hasAuthority('oa:employee:view-current')")
    public JsonResponse<Employee> getEmployeeByJobNumber(@PathVariable String jobNumber,
                                                         @RequestParam(required = false, defaultValue = "false") Boolean current) {
        if (current) {
            jobNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return JsonResponse.success(this.employeeService.findByJobNumber(jobNumber));
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('oa:employee:modify')")
    public JsonResponse<Employee> saveEmployee(@RequestBody Employee employee) {
        return JsonResponse.success(employeeService.save(employee));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('oa:employee:modify')")
    public JsonResponse<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        // 为了满足restful api
        // 校验id和实际的员工id值是否相等
        if (!Objects.equals(id, employee.getId())) {
            throw new IllegalArgumentException();
        }
        return JsonResponse.success(this.employeeService.update(employee));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('oa:employee:delete')")
    public JsonResponse<Void> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteById(id);
        return JsonResponse.success();
    }

    @PostMapping("/:deleteBatch")
    @PreAuthorize("hasAuthority('oa:employee:delete')")
    public JsonResponse<Void> deleteEmployeeBatch(@RequestBody List<String> jobNumberList) {
        employeeService.deleteBatch(jobNumberList);
        return JsonResponse.success();
    }

    @GetMapping("/count")
    @PreAuthorize("hasAuthority('oa:employee:list')")
    public JsonResponse<Long> getCount() {
        return JsonResponse.success(employeeService.count());
    }
}
