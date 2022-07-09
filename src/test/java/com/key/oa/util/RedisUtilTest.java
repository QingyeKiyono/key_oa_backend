package com.key.oa.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class RedisUtilTest {
    private final RedisUtil redisUtil;

    private static final String KEY = "key";

    private static final String VALUE = "value";

    @Autowired
    public RedisUtilTest(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @AfterEach
    public void afterEach() {
        redisUtil.deleteValue(KEY);
        System.out.println("before each");
    }

    @Test
    public void testSetAndGetValue() {
        redisUtil.setKeyValue(KEY, VALUE);
        String value = (String) this.redisUtil.getValue(KEY);
        Assert.isTrue(VALUE.equals(value), "Get and set value not equal!");
    }

    @Test
    public void testExpire() throws InterruptedException {
        redisUtil.setKeyValue(KEY, VALUE);
        long time = 2L;
        Assert.isTrue(redisUtil.setExpire(KEY, time), "Cannot set expire time!");
        Thread.sleep(1000);
        Assert.isTrue(redisUtil.getExpire(KEY) < time, "Key expire time not right!");
        Thread.sleep(1000);
        Assert.isTrue(redisUtil.getValue(KEY) == null,
                "Key doesn't expire as expected!");
    }
}
