package com.key.oa.controller;

import com.key.oa.common.JsonResponse;
import com.key.oa.entity.Employee;
import com.key.oa.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAuthority('dev:test')")
    public JsonResponse<List<Employee>> getEmployeeList(@RequestParam int page, @RequestParam int size) {
        int sizeMax = 20;

        if (size > sizeMax || page < 1) {
            throw new IllegalArgumentException();
        }

        Page<Employee> employees = this.employeeService.findAll(PageRequest.of(page - 1, size));
        return new JsonResponse<>(employees.stream().toList());
    }

    @GetMapping("/{jobNumber}")
    @PreAuthorize("hasAuthority('dev:test')")
    public JsonResponse<Employee> getEmployeeByJobNumber(@PathVariable String jobNumber) {
        return new JsonResponse<>(this.employeeService.findByJobNumber(jobNumber));
    }
}
