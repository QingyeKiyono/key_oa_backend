package com.key.oa.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.key.oa.common.JsonResponse;
import com.key.oa.dto.LoginDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PageResTest {
    private final MockMvc mockMvc;

    private final ObjectMapper mapper;

    /**
     * @see LoginTest
     */
    private static String token;

    @Autowired
    public PageResTest(MockMvc mockMvc, ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.mapper = mapper;
    }

    @BeforeEach
    public void login() throws Exception {
        LoginDTO loginDTO = new LoginDTO("20221390", "1234");

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loginDTO)))
                .andDo(result -> {
                    String content = result.getResponse().getContentAsString();
                    token = mapper.readValue(content, new TypeReference<JsonResponse<String>>() {
                    }).getData();
                });
    }

    @AfterEach
    public void logout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/logout")
                .header("token", token));
    }

    @Test
    public void testGetPagesOfCurrentEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/pages/current")
                        .header("token", token))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.data").exists())
                .andDo(MockMvcResultHandlers.print());
    }
}
