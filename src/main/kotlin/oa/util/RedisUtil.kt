package oa.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisUtil @Autowired constructor(val redisTemplate: RedisTemplate<String, Any>) {
    fun setExpire(key: String, timeoutSeconds: Long): Boolean =
        redisTemplate.expire(key, timeoutSeconds, TimeUnit.SECONDS)

    fun getExpire(key: String): Long = redisTemplate.getExpire(key)

    fun setValue(key: String, value: Any): Unit = redisTemplate.opsForValue().set(key, value)

    fun setValue(key: String, value: Any, timeoutSeconds: Long): Unit =
        redisTemplate.opsForValue().set(key, value, timeoutSeconds, TimeUnit.SECONDS)

    fun getValue(key: String): Any? = redisTemplate.opsForValue().get(key)

    fun deleteValue(key: String): Boolean = redisTemplate.delete(key)
}