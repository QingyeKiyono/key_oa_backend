package oa.service.impl

import oa.entity.Employee
import oa.repository.EmployeeRepository
import oa.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl @Autowired constructor(private val repository: EmployeeRepository) : EmployeeService {
    override fun findAll(): List<Employee> = repository.findAll()

    override fun findAll(pageable: Pageable): List<Employee> = repository.findAll(pageable).toList()

    override fun findByJobNumber(jobNumber: String): Employee? = repository.findByJobNumber(jobNumber)

    override fun save(employee: Employee): Employee {
        // 设置初始密码为'1234'，状态为'未激活'
        employee.password = BCryptPasswordEncoder().encode("1234")
        employee.verified = false

        return repository.save(employee)
    }

    override fun update(employee: Employee): Employee = repository.save(employee)

    override fun count(): Long = repository.count()

    override fun delete(vararg jobNumbers: String) {
        val employees = repository.findAllByJobNumberIn(jobNumbers.toList())
        employees?.also { repository.deleteAll(employees) }
    }
}