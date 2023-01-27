package com.key.oa.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any?> {
        val redisTemplate: RedisTemplate<String, Any?> = RedisTemplate()
        redisTemplate.setConnectionFactory(factory)

        // 设置key的序列化类型
        val stringSerializer = StringRedisSerializer()
        redisTemplate.keySerializer = stringSerializer

        // 创建ObjectMapper
        val mapper: ObjectMapper = jacksonObjectMapper()
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        mapper.activateDefaultTyping(
            LaissezFaireSubTypeValidator.instance,
            ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.PROPERTY
        )
        // 创建JsonSerializer
        val jsonSerializer: Jackson2JsonRedisSerializer<Any?> = Jackson2JsonRedisSerializer(mapper, Any::class.java)
        // 设置value的序列化类型
        redisTemplate.valueSerializer = jsonSerializer

        // 设置hash的key和value序列化类型
        redisTemplate.hashKeySerializer = stringSerializer
        redisTemplate.hashValueSerializer = jsonSerializer

        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}