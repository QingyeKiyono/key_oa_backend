package oa.event

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import oa.common.JsonResponse
import oa.dto.LoginDTO
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class LoginTest @Autowired constructor(private val mockMvc: MockMvc, private val mapper: ObjectMapper) {
    private lateinit var token: String

    @Test
    @Order(1)
    @Throws(Exception::class)
    fun testLoginSuccess() {
        val loginDTO = LoginDTO("20221390", "1234")

        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(loginDTO)
        }.andExpect {
            jsonPath("$.code") { value("00000") }
        }.andDo {
            handle {
                val content: String = it.response.contentAsString
                token = mapper.readValue(content,
                    object : TypeReference<JsonResponse<String>>() {}).data!!
            }
        }
    }

    @Test
    @Order(2)
    @Throws(java.lang.Exception::class)
    fun testLogoutSuccess() {
        mockMvc.post("/logout") {
            header("token", token)
        }.andExpect {
            status { isOk() }
            jsonPath("$.code") { value("00000") }
        }
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun testUsernameNotFound() {
        val loginDTO = LoginDTO("12341234", "1234")
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(loginDTO)
            accept = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"
        }.andExpect {
            jsonPath("$.code") { value("A0201") }
            jsonPath("$.message") { value("用户账户不存在") }
            jsonPath("$.data") { isEmpty() }
        }
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun testPasswordWrong() {
        val loginDTO = LoginDTO("20221390", "12341")
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(loginDTO)
            accept = MediaType.APPLICATION_JSON
            characterEncoding = "UTF-8"
        }.andExpect {
            jsonPath("$.code") { value("A0210") }
            jsonPath("$.message") { value("用户密码错误") }
            jsonPath("$.data") { isEmpty() }
        }
    }
}