package com.key.oa.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.key.oa.common.JsonResponse;
import com.key.oa.dto.LoginDTO;
import com.key.oa.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        String expiredToken = jwtUtil.generate("20221390", null, -1);

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
