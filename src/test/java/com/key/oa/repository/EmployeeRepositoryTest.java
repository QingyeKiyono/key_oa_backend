package com.key.oa.repository;

import com.key.oa.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Date;

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
    public void testSave() {
        long count1 = this.repository.count();
        Employee employee = new Employee();
        employee.setId(null);
        employee.setName("TestEmployee");
        employee.setBirthday(new Date());
        employee.setEmail("123@abc.com");
        employee.setJobNumber("20221325");
        employee.setPassword("megumi");
        employee.setIdentity("123456789012345678");
        employee.setPhone("12312331233");
        employee.setVerified(true);
        this.repository.save(employee);
        long count2 = this.repository.count();
        Assert.isTrue((count1 + 1) == count2, "无法正确添加员工");
    }
}
