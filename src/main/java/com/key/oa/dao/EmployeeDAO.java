package com.key.oa.dao;

import com.key.oa.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * @author 孙强
 * 员工表的DAO
 */
@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long> {
}
