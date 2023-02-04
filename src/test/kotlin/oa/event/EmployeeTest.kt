package oa.event

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import oa.common.JsonResponse
import oa.dto.LoginDTO
import oa.entity.Employee
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.Rollback
import org.springframework.test.web.servlet.*
import org.springframework.transaction.annotation.Transactional
import java.util.*

private const val REQUEST_PATH: String = "/employees"

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val mapper: ObjectMapper
) : WithAssertions {
    lateinit var token: String

    @Test
    fun testGetList() {
        // 首先测试成功返回结果
        mockMvc.get(REQUEST_PATH) {
            header("token", token)
            param("page", "1")
            param("size", "10")
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("00000") }
            jsonPath("$.data") { isNotEmpty() }
        }.andDo {
            handle {
                val content = it.response.contentAsString
                val employeeList: List<Employee> = mapper.readValue(content,
                    object : TypeReference<JsonResponse<List<Employee>>>() {}).data!!
                assertThat(employeeList)
                    .`as`("Employee length should be 10.")
                    .hasSize(10)
            }
        }

        // 测试参数不正确的预期行为
        mockMvc.get(REQUEST_PATH) {
            header("token", token)
            param("page", "0")
            param("size", "0")
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("A0402") }
            jsonPath("$.message") { value("无效的用户输入") }
            jsonPath("$.data") { isEmpty() }
        }
    }

    @Test
    fun testGetByJobNumber() {
        // 查看当前登录员工号
        mockMvc.get("$REQUEST_PATH/20221391") {
            header("token", token)
            param("current", "true")
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("00000") }
            jsonPath("$.data") { isNotEmpty() }
        }.andDo {
            handle {
                val content = it.response.contentAsString
                val employee: Employee = mapper.readValue(content,
                    object : TypeReference<JsonResponse<Employee>>() {}).data!!
                assertThat(employee)
                    .`as`("Job number should be '20221390'.")
                    .extracting("jobNumber").isEqualTo("20221390")
            }
        }

        // 查看非当前登录员工号
        mockMvc.get("$REQUEST_PATH/20224804") {
            header("token", token)
            param("current", "false")
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("00000") }
            jsonPath("$.data") { isNotEmpty() }
        }.andDo {
            handle {
                val content = it.response.contentAsString
                val employee: Employee = mapper.readValue(content,
                    object : TypeReference<JsonResponse<Employee>>() {}).data!!
                assertThat(employee)
                    .`as`("Job number should be '20224804'.")
                    .extracting("jobNumber").isEqualTo("20224804")
            }
        }
    }

    @Test
    fun testCount() {
        mockMvc.get("$REQUEST_PATH/count") {
            header("token", token)
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("00000") }
            jsonPath("$.data") { value("10") }
        }
    }

    @Test
    @Transactional
    @Rollback
    fun testSave() {
        val employee = Employee(
            id = null, jobNumber = "20222200", name = "测试", phone = "",
            email = "", password = "", birthday = Date(), verified = true, identity = "2011231231"
        )

        mockMvc.post("$REQUEST_PATH/") {
            content = mapper.writeValueAsString(employee)
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"
            header("token", token)
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("00000") }
            // 结果校验省略，因为已经在EmployeeServiceTest中测试完毕
        }
    }

    @Test
    @Transactional
    @Rollback
    fun testUpdate() {
        lateinit var employee: Employee

        mockMvc.get("$REQUEST_PATH/20221390") {
            header("token", token)
            param("current", "false")
        }.andDo {
            handle {
                employee = mapper.readValue(it.response.contentAsString,
                    object : TypeReference<JsonResponse<Employee>>() {}).data!!
            }
        }

        employee.jobNumber = "20221391"
        mockMvc.put("$REQUEST_PATH/${employee.id}") {
            header("token", token)
            content = mapper.writeValueAsString(employee)
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("00000") }
        }
    }

    @Test
    @Transactional
    @Rollback
    fun testDelete() {
        mockMvc.delete("$REQUEST_PATH/1") {
            header("token", token)
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("00000") }
        }
    }

    @Test
    @Transactional
    @Rollback
    fun testDeleteBatch() {
        mockMvc.post("$REQUEST_PATH/:deleteBatch") {
            header("token", token)
            content = mapper.writeValueAsString(listOf("20221390", "20223395"))
            contentType = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("00000") }
        }
    }

    @BeforeAll
    fun login() {
        mockMvc.post("/login") {
            content = mapper.writeValueAsString(LoginDTO("20221390", "1234"))
            contentType = MediaType.APPLICATION_JSON
        }.andDo {
            handle {
                val content = it.response.contentAsString
                token = mapper.readValue(content, object : TypeReference<JsonResponse<String>>() {}).data!!
            }
        }
    }

    @AfterAll
    fun logout() {
        mockMvc.post("/logout") { header("token", token) }
    }
}