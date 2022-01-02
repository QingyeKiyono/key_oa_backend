package com.key.oa;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Resource
    private MockMvc mockMvc;

    @Test
    public void testCount() throws Exception {
        // 测试正常的响应是否正确
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("000"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String dataString = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.data");
        int count1 = Integer.parseInt(dataString);

        // 再访问一次接口，查看两次获得的数量是否一致
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        dataString = JsonPath.read(mvcResult2.getResponse().getContentAsString(), "$.data");
        int count2 = Integer.parseInt(dataString);
        Assertions.assertEquals(count1, count2);
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("000"))
                .andDo(MockMvcResultHandlers.print());
    }
}
