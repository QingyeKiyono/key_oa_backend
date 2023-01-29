package com.key.oa.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.apache.commons.lang3.time.DateUtils
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

private const val ISSUER: String = "jyjdal"
private const val SALT: String = "1Jvq2wYsh3B2Ex9UZlNwNUMpbCILbvQt"

@Component
class JwtUtil {
    fun generate(subject: String, claims: Map<String, Any?>? = null, ttlByMinute: Int = 60 * 24): String {
        return if (claims == null) {
            builder()
        } else {
            builder().setClaims(claims)
        }
            .setSubject(subject)
            .setExpiration(DateUtils.addMinutes(Date(), ttlByMinute))
            .compact()
    }

    fun parse(token: String): Claims = parser().parseClaimsJws(token).body

    private fun builder(): JwtBuilder = Jwts.builder()
        .setIssuer(ISSUER)
        .setIssuedAt(Date())
        .setId(UUID.randomUUID().toString())
        .signWith(key())

    private fun parser(): JwtParser = Jwts.parserBuilder().setSigningKey(key()).build()

    private fun key(): Key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(SALT.toByteArray(StandardCharsets.UTF_8)))
}
