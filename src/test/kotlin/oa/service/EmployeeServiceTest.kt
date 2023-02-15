package oa.service

import oa.entity.Employee
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeServiceTest @Autowired constructor(private val employeeService: EmployeeService) : WithAssertions {
    @Test
    fun testCount() {
        val count1: Long = employeeService.count()
        val count2: Long = employeeService.count()
        assertThat(count1)
            .`as`("Two counts should be equal.")
            .isEqualTo(count2)
    }

    @Test
    fun testFindAll() {
        val list = employeeService.findAll()
        assertThat(list.size)
            .`as`("Should be 10, because there are only 10 employees.")
            .isEqualTo(10)
    }

    @Test
    fun testFindAllWithPagination() {
        var pageRequest = PageRequest.of(0, 10)
        var list = employeeService.findAll(pageRequest)
        assertThat(list.size)
            .`as`("Page list size.")
            .isEqualTo(10)
        pageRequest = PageRequest.of(0, 9)
        list = employeeService.findAll(pageRequest)
        assertThat(list.size)
            .`as`("Page with size 9.")
            .isLessThan(10)
        pageRequest = PageRequest.of(0, 12)
        list = employeeService.findAll(pageRequest)
        assertThat(list.size)
            .`as`("Only has 10 records.")
            .isEqualTo(10)
    }

    @Test
    fun testFindByJobNumber() {
        var jobNumber = "20221390"
        var employee = employeeService.findByJobNumber(jobNumber)
        assertThat(employee)
            .`as`("Found an employee.")
            .isNotNull
            .extracting("jobNumber").isEqualTo("20221390")

        jobNumber = "20221221"
        employee = employeeService.findByJobNumber(jobNumber)
        assertThat(employee)
            .`as`("Employee shouldn't exist.")
            .isNull()
    }

    @Test
    @Transactional
    @Rollback
    fun testSave() {
        var employee = Employee(
            id = null, jobNumber = "20222200", name = "测试", phone = "", gender = true,
            email = "", password = "", birthday = Date(), verified = true, identity = "2011231231"
        )

        employee = employeeService.save(employee)
        assertThat(employee)
            .`as`("Password should be '1234'.")
            .extracting("password").isEqualTo("1234")

        assertThat(employee)
            .`as`("Verified should be false.")
            .extracting("verified").isEqualTo(false)
    }

    @Test
    @Transactional
    @Rollback
    fun testUpdate() {
        var employee = employeeService.findByJobNumber("20221390")!!
        employee.jobNumber = "20221391"
        employee = employeeService.update(employee)
        assertThat(employee)
            .`as`("Job number should change.")
            .extracting("jobNumber").isEqualTo("20221391")
    }

    @Test
    @Transactional
    @Rollback
    fun testDelete() {
        val jobNumbers = arrayOf("20221390", "20223395")
        employeeService.delete(*jobNumbers)
        var count = employeeService.count()
        assertThat(count)
            .`as`("Count should be 8.")
            .isEqualTo(8L)

        employeeService.delete("20223930")
        count = employeeService.count()
        assertThat(count)
            .`as`("Count should be 7.")
            .isEqualTo(7L)
    }
}