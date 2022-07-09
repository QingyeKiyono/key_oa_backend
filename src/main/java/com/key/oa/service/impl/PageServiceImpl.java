package com.key.oa.service.impl;

import com.key.oa.entity.Employee;
import com.key.oa.entity.Page;
import com.key.oa.entity.Role;
import com.key.oa.service.EmployeeService;
import com.key.oa.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 孙强
 */
@Service
public class PageServiceImpl implements PageService {
    private final EmployeeService employeeService;

    @Autowired
    public PageServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public Set<Page> getPagesOfCurrentUser() {
        Set<Page> pages = new HashSet<>(10);

        String jobNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeService.findByJobNumber(jobNumber);
        for (Role role : employee.getRoles()) {
            pages.addAll(role.getPages());
        }
        return pages;
    }
}
