package com.key.oa.event

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.key.oa.common.JsonResponse
import com.key.oa.dto.LoginDTO
import com.key.oa.util.JwtUtil
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class LogoutTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val mapper: ObjectMapper,
    private val jwtUtil: JwtUtil
) {
    private lateinit var token: String

    @Test
    @Throws(Exception::class)
    fun testLogoutSuccess() {
        val loginDTO = LoginDTO("20221390", "1234")

        // 首先正确登录
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(loginDTO)
        }.andDo {
            handle {
                val content = it.response.contentAsString
                token = mapper.readValue(content, object : TypeReference<JsonResponse<String>>() {}).data!!
            }
        }

        // 然后进行正确的注销流程
        mockMvc.post("/logout") {
            header("token", token)
        }.andExpect {
            jsonPath("$.code") { value("00000") }
        }
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun testTokenMalformed() {
        mockMvc.post("/logout") {
            accept = MediaType.APPLICATION_JSON
            header("token", "ThisIsAMalformedToken")
        }.andExpect {
            jsonPath("$.code") { value("A0403") }
            jsonPath("$.message") { value("Token非法") }
            jsonPath("$.data") { isEmpty() }
        }
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun testTokenExpired() {
        val expiredToken = jwtUtil.generate("20221390", null, -1)
        mockMvc.post("/logout") {
            accept = MediaType.APPLICATION_JSON
            header("token", expiredToken)
        }.andExpect {
            jsonPath("$.code") { value("A0404") }
            jsonPath("$.message") { value("Token已过期") }
            jsonPath("$.data") { isEmpty() }
        }
    }
}