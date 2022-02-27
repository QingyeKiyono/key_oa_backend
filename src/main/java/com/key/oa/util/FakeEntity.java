package com.key.oa.util;

import com.github.javafaker.Faker;
import com.key.oa.entity.Employee;

import java.util.Locale;

/**
 * @author 孙强
 * <p>
 * 一些实体类的Faker实现
 */

public class FakeEntity {
    private static Faker faker;

    static {
        FakeEntity.faker = new Faker(Locale.CHINA);
    }

    public static Employee employee() {
        Employee employee = new Employee();
        employee.setId(null);
        employee.setEmail(FakeValue.email());
        employee.setRealName(faker.name().fullName());
        employee.setPassword(FakeValue.password());
        employee.setPhone(faker.phoneNumber().cellPhone());
        employee.setIdentity(FakeValue.identity());
        employee.setGender(false);
        employee.setAdmin(false);
        return employee;
    }
}
