package com.key.oa.service;

import com.key.oa.entity.Employee;

import java.util.List;
import java.util.Optional;

/**
 * @author 孙强
 */
public interface EmployeeService {
    /**
     * 添加员工
     *
     * @param employee 需要添加的员工
     */
    void save(Employee employee);

    /**
     * 根据身份证号删除员工
     *
     * @param identity 需要删除的员工的身份证号
     */
    void deleteByIdentity(String identity);

    /**
     * 查询员工的人数
     *
     * @return 员工的总数
     */
    long count();

    /**
     * 返回所有的员工
     *
     * @return 所有的员工信息
     */
    List<Employee> findAll();

    /**
     * 根据Id查找员工信息
     *
     * @param id 需要查找的员工Id
     * @return 符合条件的员工信息
     */
    Optional<Employee> findById(Long id);
}
