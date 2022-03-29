package com.key.oa.controller;

import com.key.oa.common.JsonResponse;
import com.key.oa.common.ResponseInfo;
import com.key.oa.entity.Employee;
import com.key.oa.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/count")
    public JsonResponse<Long> count() {
        return new JsonResponse<>(ResponseInfo.OK, employeeService.count());
    }

    @PostMapping("")
    public JsonResponse<Object> save(@RequestBody @Valid Employee employee) {
        this.employeeService.save(employee);
        log.info("Employee saved: {}", employee);
        return new JsonResponse<>(ResponseInfo.OK);
    }

    @GetMapping("")
    public JsonResponse<List<Employee>> findAll() {
        return new JsonResponse<>(ResponseInfo.OK, this.employeeService.findAll());
    }

    @DeleteMapping("/{id}")
    public JsonResponse<Object> deleteByIdentity(@PathVariable Long id) {
        this.employeeService.deleteById(id);
        log.info("Employee with id: {} deleted.", id);
        return new JsonResponse<>(ResponseInfo.OK);
    }

    @GetMapping("/{id}")
    public JsonResponse<Employee> findById(@PathVariable Long id) {
        Optional<Employee> result = this.employeeService.findById(id);
        return result.map(employee -> new JsonResponse<>(ResponseInfo.OK, employee)).orElseGet(() -> new JsonResponse<>(ResponseInfo.EMPLOYEE_NOT_FOUND));
    }
}
