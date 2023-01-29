package com.key.oa.annotation

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.key.oa.config.DesensitizeJsonSerializer
import kotlin.annotation.Target
import java.util.function.Function

/**
 * 数据脱敏的注解
 *
 * @author 孙强
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD, AnnotationTarget.TYPE, AnnotationTarget.PROPERTY)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizeJsonSerializer::class)
annotation class Desensitize(
    /**
     * 对数据的脱敏策略
     *
     * @return 脱敏策略
     */
    val strategy: DesensitizeStrategy
) {
    enum class DesensitizeStrategy(private val desensitizeSerializer: Function<String, String>) {
        /**
         * 对用户名进行脱敏
         */
        NAME({ it.replace("(\\S)\\S(\\S*)".toRegex(), "$1*$2") }),

        /**
         * 对身份证进行脱敏
         */
        ID_CARD({ it.replace("(\\d{4})\\d{10}(\\w{4})".toRegex(), "$1****$2") }),

        /**
         * 对电话号码进行脱敏
         */
        PHONE({ it.replace("(\\d{3})\\d{4}(\\d{4})".toRegex(), "$1****$2") }),

        /**
         * 对地址进行脱敏
         */
        ADDRESS({ it.replace("(\\S{3})\\S{2}(\\S*)\\S{2}".toRegex(), "$1****$2****") }),

        /**
         * 对密码进行脱敏，全部加密即可
         */
        PASSWORD(Function { "********" });

        fun desensitizeSerializer(): Function<String, String> {
            return desensitizeSerializer
        }
    }
}