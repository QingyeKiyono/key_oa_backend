package com.key.oa.repository;

import com.key.oa.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 孙强
 * 员工类的Repository
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    /**
     * 根据工号查找员工信息，使用Spring Data Jpa中的自带方法
     *
     * @param jobNumber 员工的工号
     * @return 查找到的员工信息
     */
    Employee findByJobNumber(String jobNumber);

    /**
     * 根据工号查找员工
     *
     * @param jobNumberList 员工工号列表
     * @return 找到的员工列表
     */
    List<Employee> findAllByJobNumberIn(List<String> jobNumberList);
}