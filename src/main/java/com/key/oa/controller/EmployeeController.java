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
        return new JsonResponse<>(employees.stream().toList());
    }

    @GetMapping("/{jobNumber}")
    @PreAuthorize("hasAuthority('oa:employee:view') or hasAuthority('oa:employee:view-current')")
    public JsonResponse<Employee> getEmployeeByJobNumber(@PathVariable String jobNumber,
                                                         @RequestParam(required = false, defaultValue = "false") Boolean current) {
        if (current) {
            jobNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return new JsonResponse<>(this.employeeService.findByJobNumber(jobNumber));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('oa:employee:modify')")
    public JsonResponse<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        // 为了满足restful api
        // 校验id和实际的员工id值是否相等
        if (!Objects.equals(id, employee.getId())) {
            throw new IllegalArgumentException();
        }
        return new JsonResponse<>(this.employeeService.update(employee));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('oa:employee:modify')")
    public JsonResponse<Void> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteById(id);
        return new JsonResponse<>();
    }
}
