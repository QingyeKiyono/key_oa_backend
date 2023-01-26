package com.key.oa.config

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class JacksonConfig {
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper::class)
    fun objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper {
        val mapper: ObjectMapper = jacksonObjectMapper()
        // Convert long to string
        mapper.configOverride(Long::class.java).format = JsonFormat.Value.forShape(JsonFormat.Shape.STRING)

        // Convert null object to an empty string
        mapper.serializerProvider.setNullValueSerializer(object : JsonSerializer<Any>() {
            override fun serialize(value: Any?, generator: JsonGenerator, serializers: SerializerProvider) {
                generator.writeString("")
            }
        })
        return mapper
    }
}