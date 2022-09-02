package com.key.oa.util;

import io.jsonwebtoken.Claims;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JwtUtilTest implements WithAssertions {
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtUtilTest(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Test
    public void testGenerate() {
        // 测试是否能够生成token
        String token1 = this.jwtUtil.generate("123");
        assertThat(token1)
                .as("Generating a token.")
                .isNotEmpty();

        // 任意两次生成的token都应当不同
        String token2 = this.jwtUtil.generate("123");
        assertThat(token1)
                .as("Comparing two tokens with the same subject.")
                .isNotEqualTo(token2)
                .isNotEmpty();
    }

    @Test
    public void testGenerateWithClaimsAndParse() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("1", 1);
        String token = this.jwtUtil.generateWithClaims("123", claims);
        Claims parsedClaims = this.jwtUtil.parse(token);
        assertThat(parsedClaims.get("1"))
                .as("Checking claim.")
                .isEqualTo(1);
    }

    @Test
    public void testSalt() {
        assertThat(jwtUtil.getSalt())
                .as("Checking salt value.")
                .isEqualTo("1Jvq2wYsh3B2Ex9UZlNwNUMpbCILbvQt");
    }
}
