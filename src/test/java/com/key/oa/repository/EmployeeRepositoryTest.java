package com.key.oa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    private static final String preloadedEntityJobNumber = "20221390";

    @Test
    public void testFindByJobNumber() {
        Assert.notNull(this.repository.findByJobNumber(preloadedEntityJobNumber),
                "No entity found!");
        Assert.isNull(this.repository.findByJobNumber("-12345678"),
                "Entity with illegal job number found!");
    }
}
