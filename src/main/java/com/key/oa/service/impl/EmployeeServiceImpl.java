package com.key.oa.service.impl;

import com.key.oa.dao.EmployeeDAO;
import com.key.oa.entity.Employee;
import com.key.oa.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 孙强
 * 员工操作的具体实现
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Resource
    private EmployeeDAO employeeDAO;

    @Override
    public void save(Employee employee) {
        this.employeeDAO.save(employee);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIdentity(String identity) {
        this.employeeDAO.deleteByIdentity(identity);
    }

    @Override
    public long count() {
        return this.employeeDAO.count();
    }

    @Override
    public List<Employee> findAll() {
        return this.employeeDAO.findAll();
    }
}
