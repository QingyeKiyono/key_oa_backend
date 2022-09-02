package com.key.oa.service;

import com.key.oa.entity.Employee;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeServiceTest implements WithAssertions {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeServiceTest(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Test
    public void testCount() {
        long count1 = employeeService.count();
        long count2 = employeeService.count();
        assertThat(count1)
                .as("Two counts.")
                .isEqualTo(count2);
    }

    @Test
    public void testFindAll() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Employee> list = employeeService.findAll(pageRequest).toList();
        assertThat(list.size())
                .as("Page list size.")
                .isEqualTo(10);

        pageRequest = PageRequest.of(0, 9);
        list = employeeService.findAll(pageRequest).toList();
        assertThat(list.size())
                .as("Page with size 9.")
                .isLessThan(10);

        pageRequest = PageRequest.of(0, 12);
        list = employeeService.findAll(pageRequest).toList();
        assertThat(list.size())
                .as("Only has 10 records.")
                .isEqualTo(10);
    }
}
