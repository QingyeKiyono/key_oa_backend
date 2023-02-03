package oa.controller

import oa.common.JsonResponse
import oa.dto.LoginDTO
import oa.service.LoginService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(private var loginService: LoginService) {
    @PostMapping("/login")
    fun login(
        @RequestBody @Validated loginDTO: LoginDTO
    ): JsonResponse<String> = loginService.login(loginDTO)

    @PostMapping("/logout")
    fun logout(): JsonResponse<Unit> = loginService.logout()
}