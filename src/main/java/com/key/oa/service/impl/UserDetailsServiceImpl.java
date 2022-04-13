package com.key.oa.service.impl;

import com.key.oa.dto.LoginEmployee;
import com.key.oa.entity.Employee;
import com.key.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author 孙强
 * 实现UserDetailsService，从数据库中查找数据
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final EmployeeService employeeService;

    @Autowired
    public UserDetailsServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public UserDetails loadUserByUsername(String jobNumber) throws UsernameNotFoundException {
        // 根据登录的工号查询员工信息
        Employee employee = employeeService.findByJobNumber(jobNumber);
        if (employee == null) {
            throw new RuntimeException(String.format("No employee found with job number: %s.", jobNumber));
        }

        // 查询用户权限信息

        // 把员工信息封装成UserDetails对象返回

        return new LoginEmployee(employee);
    }
}
