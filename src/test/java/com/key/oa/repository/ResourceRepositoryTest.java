package com.key.oa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ResourceRepositoryTest {

    private final ResourceRepository repository;

    @Autowired
    public ResourceRepositoryTest(ResourceRepository repository) {
        this.repository = repository;
    }

    @Test
    public void testCount() {
        Assert.isTrue(repository.getResourceByPageIsTrue().size() == 1, "");
    }
}
