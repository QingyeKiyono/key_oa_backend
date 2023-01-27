package com.key.oa.util

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private const val KEY = "key"

private const val VALUE = "value"

@SpringBootTest
class RedisUtilTest @Autowired constructor(val redisUtil: RedisUtil) : WithAssertions {
    @AfterEach
    fun deleteKey() {
        redisUtil.deleteValue(KEY)
    }

    @Test
    fun testGetAndSet() {
        redisUtil.setValue(KEY, VALUE)
        val value: String = this.redisUtil.getValue(KEY)!! as String
        assertThat(value)
            .`as`("Getting and setting redis value.")
            .isEqualTo(VALUE)
    }

    @Test
    @Throws(InterruptedException::class)
    fun testExpire() {
        redisUtil.setValue(KEY, VALUE)
        val time = 1L
        assertThat(redisUtil.setExpire(KEY, time))
            .`as`("Setting expire.")
            .isTrue
        Thread.sleep(500)
        assertThat(redisUtil.getExpire(KEY))
            .`as`("Checking time before key expires.")
            .isLessThan(time)
        Thread.sleep(500)
        assertThat(redisUtil.getValue(KEY))
            .`as`("Getting value after it expires.")
            .isNull()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testExpire2() {
        val time = 1L
        redisUtil.setValue(KEY, VALUE, time)
        Thread.sleep(500)
        assertThat(redisUtil.getExpire(KEY))
            .`as`("Checking time before key expires.")
            .isLessThan(time)
        Thread.sleep(500)
        assertThat(redisUtil.getValue(KEY))
            .`as`("Getting value after it expires.")
            .isNull()
    }
}