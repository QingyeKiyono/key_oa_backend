package com.key.oa.service.impl

import com.key.oa.entity.Employee
import com.key.oa.repository.EmployeeRepository
import com.key.oa.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeServiceImpl @Autowired constructor(private val repository: EmployeeRepository) : EmployeeService {
    override fun findAll(pageable: Pageable): Page<Employee> = repository.findAll(pageable)

    override fun findByJobNumber(jobNumber: String): Employee? = repository.findByJobNumber(jobNumber)

    override fun save(employee: Employee): Employee {
        // 设置初始密码为'1234'，状态为'未激活'
        employee.password = "1234"
        employee.verified = false

        return repository.save(employee)
    }

    override fun update(employee: Employee): Employee = repository.save(employee)

    override fun deleteById(id: Long) = repository.deleteById(id)

    override fun count(): Long = repository.count()

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteBatch(jobNumberList: List<String>) {
        val employeeList = repository.findAllByJobNumberIn(jobNumberList)
        if (employeeList != null) {
            repository.deleteAllInBatch(employeeList)
        }
    }
}