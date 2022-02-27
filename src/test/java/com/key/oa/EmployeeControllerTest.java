package com.key.oa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.key.oa.common.ResponseInfo;
import com.key.oa.entity.Employee;
import com.key.oa.util.FakeEntity;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJson
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerTest {
    @Resource
    private ObjectMapper mapper;

    @Resource
    private MockMvc mockMvc;

    /**
     * 测试返回数据的基本格式是否正确
     */
    @Test
    public void testResponseFormat() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ResponseInfo.OK.getMessage()))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 两次访问count接口，保证幂等性
     */
    @Test
    public void testCount() throws Exception {
        String jsonResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String dataString = JsonPath.read(jsonResult, "$.data");
        int count1 = Integer.parseInt(dataString);

        jsonResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        dataString = JsonPath.read(jsonResult, "$.data");
        int count2 = Integer.parseInt(dataString);
        Assertions.assertEquals(count1, count2);
    }

    /**
     * 测试查询所有员工的接口是否正常工作
     * 是否返回正常结果需要查看Response的data项以及数据库中的内容
     */
    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 测试保存员工的接口是否正确，传给接口的内容一次正确一次不正确
     *
     * @throws Exception 除了接口访问异常之外还可能有Employee不符合要求的异常
     */
    @Test
    @Transactional
    public void testSave() throws Exception {
        Employee employee = FakeEntity.employee();

        String content = mapper.writeValueAsString(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()));

        mockMvc.perform(MockMvcRequestBuilders.post("/employee/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(mapper.writeValueAsString(new Employee()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 通过先增加再删除的方式验证删除员工的接口是否正常工作
     */
    @Test
    @Transactional
    public void testDeleteByIdentity() throws Exception {
        Employee employee = FakeEntity.employee();

        // 设置身份证号
        String identity = employee.getIdentity();

        // 记录添加员工之前的员工总数
        String jsonResult = mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
                .andReturn()
                .getResponse()
                .getContentAsString();
        String countString = JsonPath.read(jsonResult, "$.data");

        // 添加员工
        String content = mapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/").accept(MediaType.APPLICATION_JSON).content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // 删除员工
        mockMvc.perform(MockMvcRequestBuilders.delete("/employee/").accept(MediaType.APPLICATION_JSON).content(identity).contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
                .andDo(MockMvcResultHandlers.print());

        // 验证添加前和删除后的员工数量一致
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/count").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(countString));
    }

    /**
     * 测试按照指定id查找员工信息的接口
     * 需要放在第一个执行
     */
    @Test
    @Transactional
    @Order(1)
    public void testFindById() throws Exception {
        // 测试必然会失败的情况
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/-1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.EMPLOYEE_NOT_FOUND.getCode()));

        // 添加一个员工，此时只有一个员工
        Employee employee = FakeEntity.employee();
        String content = mapper.writeValueAsString(employee);
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // 此时能够找到id为1的员工信息
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ResponseInfo.OK.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }
}
