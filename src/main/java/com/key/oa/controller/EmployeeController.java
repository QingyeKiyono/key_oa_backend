package com.key.oa.controller;

import com.key.oa.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 孙强
 * 员工操作的接口
 */
@Slf4j
@RestController
@RequestMapping(value = "/employee", produces = "application/json; charset=UTF-8")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;
}
