package com.key.oa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

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

    @Test
    @Transactional
    @Rollback
    public void testFindAllByJobNumberIn() {
        List<String> jobNumberList = Arrays.asList("20221390", "20223395", "20221375");
        int size = repository.findAllByJobNumberIn(jobNumberList).size();
        Assert.isTrue(size == 3, "查找数量不正确！");

        jobNumberList = Arrays.asList("20221390", "20213395", "20211375");
        size = repository.findAllByJobNumberIn(jobNumberList).size();
        Assert.isTrue(size == 1, "查找数量不正确！");
    }
}
