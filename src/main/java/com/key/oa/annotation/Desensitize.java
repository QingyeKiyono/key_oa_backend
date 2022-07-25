package com.key.oa.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.key.oa.config.DesensitizeJsonSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Function;

/**
 * 数据脱敏的注解
 *
 * @author 孙强
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizeJsonSerializer.class)
public @interface Desensitize {
    /**
     * 对数据的脱敏策略
     *
     * @return 脱敏策略
     */
    DesensitizeStrategy strategy();

    enum DesensitizeStrategy {
        /**
         * 对用户名进行脱敏
         */
        NAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),

        /**
         * 对身份证进行脱敏
         */
        ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2")),

        /**
         * 对电话号码进行脱敏
         */
        PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),

        /**
         * 对地址进行脱敏
         */
        ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****")),


        /**
         * 对密码进行脱敏，全部加密即可
         */
        PASSWORD(s -> "********");

        private final Function<String, String> desensitizeSerializer;

        DesensitizeStrategy(Function<String, String> desensitizeSerializer) {
            this.desensitizeSerializer = desensitizeSerializer;
        }

        public Function<String, String> desensitizeSerializer() {
            return desensitizeSerializer;
        }
    }
}
