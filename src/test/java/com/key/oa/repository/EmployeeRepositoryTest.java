package com.key.oa.repository;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest implements WithAssertions {
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    private static final String preloadedEntityJobNumber = "20221390";

    @Test
    @Transactional
    @Rollback
    public void testFindByJobNumber() {
        assertThat(repository.findByJobNumber(preloadedEntityJobNumber))
                .as("Get an preloaded employee.")
                .isNotNull()
                .hasFieldOrPropertyWithValue("jobNumber", preloadedEntityJobNumber);
        assertThat(repository.findByJobNumber("-12345678"))
                .as("Fetch employee which doesn't exist.")
                .isNull();
    }

    @Test
    @Transactional
    @Rollback
    public void testFindAllByJobNumberIn() {
        List<String> jobNumberList = Arrays.asList("20221390", "20223395", "20221375");
        int size = repository.findAllByJobNumberIn(jobNumberList).size();
        assertThat(size)
                .as("All employees exist.")
                .isEqualTo(3);

        jobNumberList = Arrays.asList("20221390", "20213395", "20211375");
        size = repository.findAllByJobNumberIn(jobNumberList).size();
        assertThat(size)
                .as("Not all employees exist.")
                .isLessThan(3);
    }
}
