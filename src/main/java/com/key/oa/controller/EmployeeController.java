package com.key.oa.controller;

import com.key.oa.common.JsonResponse;
import com.key.oa.common.ResponseInfo;
import com.key.oa.entity.Employee;
import com.key.oa.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
    public JsonResponse<Long> count() {
        return new JsonResponse<>(ResponseInfo.OK, employeeService.count());
    }

    @PostMapping("/")
    public JsonResponse<Object> save(@RequestBody @Valid Employee employee) {
        this.employeeService.save(employee);
        return new JsonResponse<>(ResponseInfo.OK);
    }

    @GetMapping("/")
    public JsonResponse<List<Employee>> findAll() {
        return new JsonResponse<>(ResponseInfo.OK, this.employeeService.findAll());
    }

    @DeleteMapping("/")
    public JsonResponse<Object> deleteByIdentity(@RequestBody String identity) {
        this.employeeService.deleteByIdentity(identity);
        return new JsonResponse<>(ResponseInfo.OK);
    }
}
