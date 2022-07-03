package com.key.oa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PermissionRepositoryTest {

    private final PermissionRepository repository;

    @Autowired
    public PermissionRepositoryTest(PermissionRepository permissionRepository) {
        this.repository = permissionRepository;
    }
}
