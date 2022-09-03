package com.key.oa.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.key.oa.common.JsonResponse;
import com.key.oa.dto.LoginDTO;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    private final MockMvc mockMvc;

    private final ObjectMapper mapper;

    /**
     * 访问需要授权的URL时需要携带的token，放在header中
     * 注意！JUnit中测试方法无法共享非静态变量！
     */
    private static String token;

    @Autowired
    public LoginTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.mapper = objectMapper;
    }

    @Test
    @Order(1)
    public void testLoginSuccess() throws Exception {
        LoginDTO loginDTO = new LoginDTO("20221390", "1234");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code").value("00000"))
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    token = mapper.readValue(content, new TypeReference<JsonResponse<String>>() {
                    }).getData();
                });
    }

    @Test
    public void testUsernameNotFound() throws Exception {
        LoginDTO loginDTO = new LoginDTO("12341234", "1234");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code").value("A0201"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message").value("用户账户不存在"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data").isEmpty());
    }

    @Test
    public void testPasswordWrong() throws Exception {
        LoginDTO loginDTO = new LoginDTO("20221390", "12341");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO))
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code").value("A0210"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.message").value("用户密码错误"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data").isEmpty());
    }

    @Test
    @Order(2)
    public void testLogoutSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/logout")
                        .header("token", token))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.code").value("00000"));
    }
}
