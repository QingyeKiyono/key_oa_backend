package com.key.oa.event

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.key.oa.common.JsonResponse
import com.key.oa.dto.LoginDTO
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class PageResTest @Autowired constructor(private val mockMvc: MockMvc, private val mapper: ObjectMapper) {
    private lateinit var token: String

    @BeforeAll
    @Throws(Exception::class)
    fun login() {
        val loginDTO = LoginDTO("20221390", "1234")
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = mapper.writeValueAsString(loginDTO)
        }.andDo {
            handle {
                val content: String = it.response.contentAsString
                token = mapper.readValue(content,
                    object : TypeReference<JsonResponse<String>>() {}).data!!
            }
        }
    }

    @AfterAll
    @Throws(java.lang.Exception::class)
    fun logout() {
        mockMvc.post("/logout") { header("token", token) }
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun testGetPagesOfCurrentEmployee() {
        mockMvc.get("/pages/current") {
            header("token", token)
        }.andExpect {
            jsonPath("$.data") { exists() }
        }.andDo { print() }
    }
}