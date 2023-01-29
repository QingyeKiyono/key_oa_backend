package com.key.oa.util

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtUtilTest @Autowired constructor(val jwtUtil: JwtUtil) : WithAssertions {
    @Test
    fun testGenerate() {
        // 测试是否能够生成token
        val token1 = jwtUtil.generate("123")
        assertThat(token1)
            .`as`("Generating a token")
            .isNotEmpty

        // 任意两次生成的token都应当不同
        val token2 = jwtUtil.generate("123")
        assertThat(token1)
            .`as`("Comparing two tokens with the same subject.")
            .isNotEqualTo(token2)
            .isNotEmpty
    }

    @Test
    fun testParse() {
        val claims: MutableMap<String, Any> = HashMap()
        claims["1"] = 1
        val token = jwtUtil.generate("123", claims)
        val parsedClaims = jwtUtil.parse(token)
        assertThat(parsedClaims["1"])
            .`as`("Checking claim.")
            .isEqualTo(1)
    }
}