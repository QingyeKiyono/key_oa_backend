package com.key.oa.controller;

import com.key.oa.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 孙强
 * 员工操作的接口
 */
@RestController
@RequestMapping("employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @GetMapping("/count")
    public long count() {
        return employeeService.count();
    }
}
