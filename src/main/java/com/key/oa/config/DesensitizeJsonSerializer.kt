package com.key.oa.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.ContextualSerializer
import com.key.oa.annotation.Desensitize
import com.key.oa.annotation.Desensitize.DesensitizeStrategy

class DesensitizeJsonSerializer : JsonSerializer<String>(), ContextualSerializer {
    private lateinit var strategy: DesensitizeStrategy

    override fun serialize(value: String, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeString(strategy.desensitizeSerializer().apply(value))
    }

    override fun createContextual(prov: SerializerProvider, property: BeanProperty): JsonSerializer<*> {
        val annotation: Desensitize? = property.getAnnotation(Desensitize::class.java)
        if (annotation !== null && property.type.rawClass == String::class.java) {
            this.strategy = annotation.strategy
            return this
        }
        return prov.findValueSerializer(property.type, property)
    }
}