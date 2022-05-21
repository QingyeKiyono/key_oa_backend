package com.key.oa.service;

import com.key.oa.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author 孙强
 */
public interface EmployeeService {
    /**
     * 返回所有员工的信息
     *
     * @param pageable 分页信息
     * @return 所有的员工信息
     */
    Page<Employee> findAll(Pageable pageable);

    /**
     * 根据工号查找员工信息
     *
     * @param jobNumber 员工的工号
     * @return 对应的员工信息
     */
    Employee findByJobNumber(String jobNumber);

    /**
     * 更新员工的信息
     *
     * @param employee 需要更新的员工信息，要确保Id存在
     * @return 更新后的员工信息
     */
    Employee update(Employee employee);

    /**
     * 根据Id删除员工信息
     *
     * @param id 需要删除的员工id
     */
    void deleteById(Long id);
}
