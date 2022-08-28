package com.key.oa.service.impl;

import com.key.oa.entity.Employee;
import com.key.oa.entity.PageRes;
import com.key.oa.entity.Role;
import com.key.oa.repository.PageResRepository;
import com.key.oa.service.EmployeeService;
import com.key.oa.service.PageResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 孙强
 */
@Service
public class PageResResServiceImpl implements PageResService {
    private final EmployeeService employeeService;

    private final PageResRepository repository;

    @Autowired
    public PageResResServiceImpl(EmployeeService employeeService, PageResRepository pageResRepository) {
        this.employeeService = employeeService;
        this.repository = pageResRepository;
    }

    @Override
    public Set<PageRes> getPageResOfCurrentUser() {
        Set<PageRes> pageRes = new HashSet<>(10);

        String jobNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Employee employee = employeeService.findByJobNumber(jobNumber);
        for (Role role : employee.getRoles()) {
            pageRes.addAll(role.getPageRes());
        }
        return pageRes;
    }

    @Override
    public Page<PageRes> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
