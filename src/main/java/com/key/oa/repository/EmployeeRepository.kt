package com.key.oa.repository

import com.key.oa.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    /**
     * 根据工号查找员工
     *
     * @param jobNumber 需要查找的工号
     * @return 查找到的员工
     */
    fun findByJobNumber(jobNumber: String): Employee?

    /**
     * 根据工号查找多个员工
     *
     * @param jobNumbers 需要查找的工号列表
     * @return 查找到的员工
     */
    fun findAllByJobNumberIn(jobNumbers: List<String>): List<Employee>?
}