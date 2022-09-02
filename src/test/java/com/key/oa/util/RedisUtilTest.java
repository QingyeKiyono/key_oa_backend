package com.key.oa.util;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisUtilTest implements WithAssertions {
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
    }

    @Test
    public void testSetAndGetValue() {
        redisUtil.setKeyValue(KEY, VALUE);
        String value = (String) this.redisUtil.getValue(KEY);
        assertThat(value)
                .as("Getting and setting redis value.")
                .isEqualTo(VALUE);
    }

    @Test
    public void testExpire() throws InterruptedException {
        redisUtil.setKeyValue(KEY, VALUE);
        long time = 2L;
        assertThat(redisUtil.setExpire(KEY, time))
                .as("Setting expire.")
                .isTrue();
        Thread.sleep(1000);
        assertThat(redisUtil.getExpire(KEY))
                .as("Checking time before key expires.")
                .isLessThan(time);
        Thread.sleep(1000);
        assertThat(redisUtil.getValue(KEY))
                .as("Getting value after it expires.")
                .isNull();
    }
}
