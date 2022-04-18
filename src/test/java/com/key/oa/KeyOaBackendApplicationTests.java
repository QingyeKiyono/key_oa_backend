package com.key.oa;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@SpringBootTest
@Suite
@WebAppConfiguration
class KeyOaBackendApplicationTests {
    @Test
    public void generateKey() {
        Key privateKey = Keys.hmacShaKeyFor(
                Base64.getEncoder()
                        .encode("1Jvq2wYsh3B2Ex9UZlNwNUMpbCILbvQt".getBytes(StandardCharsets.UTF_8)));
        System.out.println(privateKey.getAlgorithm());
        Key anotherKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        System.out.println(anotherKey.getAlgorithm());
    }
}
