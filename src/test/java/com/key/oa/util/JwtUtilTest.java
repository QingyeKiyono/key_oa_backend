package com.key.oa.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
public class JwtUtilTest {
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtUtilTest(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Test
    public void testGenerate() {
        // 测试是否能够生成token
        String token1 = this.jwtUtil.generate("123");
        Assert.hasLength(token1, "Generate failed!");

        // 任意两次生成的token都应当不同
        String token3 = this.jwtUtil.generate("456");
        Assert.isTrue(!Objects.equals(token1, token3), "Generated token equal!");
    }

    @Test
    public void testGenerateWithClaimsAndParse() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("1", 1);
        String token = this.jwtUtil.generateWithClaims("123", claims);
        Claims parsedClaims = this.jwtUtil.parse(token);
        Assert.isTrue(Objects.equals(parsedClaims.get("1"), 1),
                "Claim parsed not equal to the original one.");
    }

    @Test
    public void testSalt() {
        Assert.isTrue("1Jvq2wYsh3B2Ex9UZlNwNUMpbCILbvQt".equals(jwtUtil.getSalt()),
                "Salt not match!");
    }
}
