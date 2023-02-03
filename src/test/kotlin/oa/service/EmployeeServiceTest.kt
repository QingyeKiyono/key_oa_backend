package oa.service

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest

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
        var pageRequest = PageRequest.of(0, 10)
        var list = employeeService.findAll(pageRequest).toList()
        assertThat(list.size)
            .`as`("Page list size.")
            .isEqualTo(10)
        pageRequest = PageRequest.of(0, 9)
        list = employeeService.findAll(pageRequest).toList()
        assertThat(list.size)
            .`as`("Page with size 9.")
            .isLessThan(10)
        pageRequest = PageRequest.of(0, 12)
        list = employeeService.findAll(pageRequest).toList()
        assertThat(list.size)
            .`as`("Only has 10 records.")
            .isEqualTo(10)
    }
}