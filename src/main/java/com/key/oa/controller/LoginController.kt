package com.key.oa.controller

import com.key.oa.common.JsonResponse
import com.key.oa.dto.LoginDTO
import com.key.oa.service.LoginService
import lombok.extern.slf4j.Slf4j
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class LoginController(private var loginService: LoginService) {
    @PostMapping("/login")
    fun login(
        @RequestBody @Validated loginDTO: LoginDTO
    ): JsonResponse<String> = loginService.login(loginDTO)

    @PostMapping("/logout")
    fun logout(): JsonResponse<Unit> = loginService.logout()
}