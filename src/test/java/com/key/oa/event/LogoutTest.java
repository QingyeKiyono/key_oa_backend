package com.key.oa.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.key.oa.common.JsonResponse;
import com.key.oa.dto.LoginDTO;
import com.key.oa.util.JwtUtil;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class LogoutTest {
    private final MockMvc mockMvc;

    private final ObjectMapper mapper;

    private final JwtUtil jwtUtil;

    @Autowired
    public LogoutTest(MockMvc mockMvc, ObjectMapper mapper, JwtUtil jwtUtil) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
    }

    private static String token;

    @Test
    public void testLogoutSuccess() throws Exception {
        LoginDTO loginDTO = new LoginDTO("20221390", "1234");

        // 首先正确登录
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO)))
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    token = mapper.readValue(content, new TypeReference<JsonResponse<String>>() {
                    }).getData();
                });

        // 然后进行正确的注销流程
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/logout")
                        .header("token", token))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code").value("00000"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message").value("OK"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data").isEmpty());
    }

    @Test
    public void testTokenMalformed() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/logout")
                        .header("token", "ThisIsAMalformedToken")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code").value("A0403"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message").value("Token非法"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data").isEmpty());
    }

    @Test
    public void testTokenExpired() throws Exception {
        // 这里把JwtUtil中的变量放进来
        String issuer = jwtUtil.getIssuer();
        Integer ttlByMinute = jwtUtil.getTtlByMinute();
        String salt = jwtUtil.getSalt();
        Key key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(salt.getBytes(StandardCharsets.UTF_8)));

        JwtBuilder builder = Jwts.builder()
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                // 默认失效时间为1天
                .setExpiration(DateUtils.addMinutes(new Date(), (-1 * ttlByMinute)))
                .setId(UUID.randomUUID().toString())
                .signWith(key);

        String expiredToken = builder.setSubject("20221390").compact();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/logout")
                        .header("token", expiredToken)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code").value("A0404"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message").value("Token已过期"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data").isEmpty());
    }
}
