package com.key.oa.service;

import com.key.oa.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeServiceTest {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeServiceTest(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Test
    public void testCount() {
        long count1 = employeeService.count();
        long count2 = employeeService.count();
        Assert.isTrue(count1 == count2, "两次查询数量不一致！");
    }

    @Test
    public void testFindAll() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Employee> list = employeeService.findAll(pageRequest).toList();
        Assert.isTrue(10 == list.size(), "查询数量不正确！");

        pageRequest = PageRequest.of(0, 9);
        list = employeeService.findAll(pageRequest).toList();
        Assert.isTrue(10 > list.size(), "查询数量不正确！");

        pageRequest = PageRequest.of(0, 12);
        list = employeeService.findAll(pageRequest).toList();
        Assert.isTrue(10 == list.size(), "查询数量不正确！");
    }
}
