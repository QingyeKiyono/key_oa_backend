package com.key.oa.util;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author 孙强
 * Jwt基本功能的封装
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = "key-oa.jwt")
public class JwtUtil {
    /**
     * 默认有效时间，默认1天
     */
    private Integer ttlByMinute = 60 * 24;

    /**
     * 证书签发方
     */
    private String issuer = "jyjdal";

    /**
     * 加密时使用的盐
     */
    private String salt;

    public String generate() {
        return builder().compact();
    }

    private JwtBuilder builder() {
        return Jwts.builder()
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                // 默认失效时间为1天
                .setExpiration(DateUtils.addMinutes(new Date(), ttlByMinute))
                .setId(UUID.randomUUID().toString())
                .signWith(Keys.hmacShaKeyFor(Base64.getEncoder().encode(salt.getBytes(StandardCharsets.UTF_8))));
    }
}
