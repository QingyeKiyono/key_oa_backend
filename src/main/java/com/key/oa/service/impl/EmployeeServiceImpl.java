package com.key.oa.service.impl;

import com.key.oa.entity.Employee;
import com.key.oa.repository.EmployeeRepository;
import com.key.oa.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 孙强
 * 员工类的Service实现类
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Employee findByJobNumber(String jobNumber) {
        return repository.findByJobNumber(jobNumber);
    }

    @Override
    public Employee save(Employee employee) {
        // 对密码进行加密，初始密码是1234
        employee.setPassword(new BCryptPasswordEncoder().encode("1234"));
        // 新建员工要设置成“未激活”状态
        employee.setVerified(false);
        return repository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(List<String> jobNumberList) {
        List<Employee> employeeList = repository.findAllByJobNumberIn(jobNumberList);
        if (employeeList != null) {
            repository.deleteAllInBatch(employeeList);
        }
    }
}
