package com.key.oa.repository

import com.key.oa.entity.Employee
import jakarta.transaction.Transactional
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback

private const val PRELOADED_JOB_NUMBER = "20221390"

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest @Autowired constructor(val repository: EmployeeRepository) : WithAssertions {
    @Test
    @Transactional
    @Rollback
    fun testFindByJobNumber() {
        assertThat<Employee>(repository.findByJobNumber(PRELOADED_JOB_NUMBER))
            .`as`("Get an preloaded employee.")
            .isNotNull
            .hasFieldOrPropertyWithValue("jobNumber", PRELOADED_JOB_NUMBER)
        assertThat<Employee>(repository.findByJobNumber("-12345678"))
            .`as`("Fetch employee which doesn't exist.")
            .isNull()
    }

    @Test
    @Transactional
    @Rollback
    fun testFindAllByJobNumberIn() {
        var jobNumberList: List<String> = listOf("20221390", "20223395", "20221375")
        var size = repository.findAllByJobNumberIn(jobNumberList)?.size
        assertThat(size)
            .`as`("All employees exist.")
            .isEqualTo(3)

        jobNumberList = listOf("20221390", "20213395", "20211375")
        size = repository.findAllByJobNumberIn(jobNumberList)?.size
        assertThat(size)
            .`as`("Not all employees exist.")
            .isLessThan(3)
    }
}