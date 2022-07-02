package com.key.oa.service.impl;

import com.key.oa.domain.LoginEmployee;
import com.key.oa.entity.Resource;
import com.key.oa.entity.Employee;
import com.key.oa.entity.Role;
import com.key.oa.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 孙强
 * 实现UserDetailsService，从数据库中查找数据
 */
@Slf4j
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
            throw new UsernameNotFoundException(String.format("No employee found with job number: %s.", jobNumber));
        }
        Set<Role> reachableRoles = getReachableRoles(employee.getRoles());

        // 把员工信息封装成UserDetails对象返回
        return new LoginEmployee(employee, getAuthorities(reachableRoles));
    }

    private Set<Role> getReachableRoles(Collection<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptySet();
        }
        Set<Role> unprocessedRoles = new HashSet<>(10);
        unprocessedRoles.addAll(roles);

        Set<Role> reachableRoles = new HashSet<>(20);

        while (!unprocessedRoles.isEmpty()) {
            for (Role role : unprocessedRoles) {
                reachableRoles.add(role);

                //noinspection AlibabaDontModifyInForeachCircle
                unprocessedRoles.remove(role);
                unprocessedRoles.addAll(role.getChildren());
            }
        }

        return reachableRoles;
    }

    private List<String> getAuthorities(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }

        List<Resource> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.addAll(role.getResources()));
        return authorities.stream().map(Resource::getValue).toList();
    }
}
