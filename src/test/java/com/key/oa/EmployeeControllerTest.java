package com.key.oa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.jayway.jsonpath.JsonPath;
import com.key.oa.common.ResponseInfo;
import com.key.oa.entity.Employee;
import com.key.oa.util.FakeValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.util.Locale;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJson
public class EmployeeControllerTest {
    private Faker faker;
    @Resource
    private ObjectMapper mapper;

    @Resource
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.faker = new Faker(Locale.CHINA);
    }

    @Test
    public void testCount() throws Exception {
        // 测试正常的响应是否正确
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testSave() throws Exception {
        Employee employee = new Employee(
                null, faker.name().fullName(), FakeValue.password(), FakeValue.email()
                , faker.phoneNumber().cellPhone(), true, FakeValue.identity(), false);

        String content = mapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/").accept(MediaType.APPLICATION_JSON).content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()));
    }

    @Test
    public void testDeleteByIdentity() throws Exception {
        Employee employee = new Employee(
                null, faker.name().fullName(), FakeValue.password(), FakeValue.email(),
                faker.phoneNumber().cellPhone(), true, FakeValue.identity(), false);

        // 设置身份证号
        String identity = FakeValue.identity();
        employee.setIdentity(identity);

        // 记录添加员工之前的员工总数
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
                .andReturn();

        String countString = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.data");

        // 添加员工
        String content = mapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/").accept(MediaType.APPLICATION_JSON).content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()));

        // 删除员工，验证添加前和删除后的员工数量一致
        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/").accept(MediaType.APPLICATION_JSON).content(identity).contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(countString));

    }
}
