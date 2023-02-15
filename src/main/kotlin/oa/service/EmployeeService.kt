package oa.service

import oa.entity.Employee
import org.springframework.data.domain.Pageable

interface EmployeeService {
    /**
     * 返回所有的员工信息，不带分页
     *
     * @return 全部的员工信息
     */
    fun findAll(): List<Employee>

    /**
     * 返回所有员工的信息，带有分页信息
     *
     * @param pageable 分页信息
     * @return 所有的员工信息
     */
    fun findAll(pageable: Pageable): List<Employee>

    /**
     * 根据工号查找员工信息
     *
     * @param jobNumber 员工的工号
     * @return 对应的员工信息
     */
    fun findByJobNumber(jobNumber: String): Employee?

    /**
     * 新建员工信息
     *
     * @param employee 需要创建的员工信息
     * @return 创建后的员工信息
     */
    fun save(employee: Employee): Employee

    /**
     * 更新员工的信息
     *
     * @param employee 需要更新的员工信息，要确保Id存在
     * @return 更新后的员工信息
     */
    fun update(employee: Employee): Employee

    /**
     * 查看员工数量
     *
     * @return 所有的员工数量
     */
    fun count(): Long

    /**
     * 根据工号删除员工信息
     */
    fun delete(vararg jobNumbers: String)
}