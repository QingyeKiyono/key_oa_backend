package com.key.oa.controller;

import com.key.oa.common.JsonResponse;
import com.key.oa.entity.Employee;
import com.key.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 和员工相关的操作
 *
 * @author 孙强
 */
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

        if (size > sizeMax) {
            throw new IllegalArgumentException();
        }

        Page<Employee> employees = this.employeeService.findAll(PageRequest.of(page, size));
        return new JsonResponse<>(employees.stream().toList());
    }
}
