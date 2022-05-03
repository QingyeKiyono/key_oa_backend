package com.key.oa.util;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class RedisUtilTest {
    private final RedisUtil redisUtil;

    @Autowired
    public RedisUtilTest(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Test
    public void testSetAndGetValue() {
        this.redisUtil.setKeyValue("key", "value");
        String value = (String) this.redisUtil.getValue("key");
        Assert.isTrue("value".equals(value), "Get and set value not equal!");
    }
}
