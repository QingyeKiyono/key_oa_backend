package com.key.oa.dao;

import com.key.oa.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * @author 孙强
 * 员工表的DAO
 */
@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long> {
    /**
     * 根据身份证号删除员工信息
     *
     * @param identity 需要删除的员工的身份证号
     */
    @Modifying
    @Query("DELETE FROM Employee WHERE identity = :identity ")
    void deleteByIdentity(String identity);
}
