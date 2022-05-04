package com.key.oa.controller;

import com.key.oa.common.JsonResponse;
import com.key.oa.dto.LoginDTO;
import com.key.oa.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 孙强
 * 用于实现登录
 */
@Slf4j
@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public JsonResponse<String> login(@RequestBody @Validated LoginDTO loginDTO) {
        return loginService.login(loginDTO);
    }

    @PostMapping("/logout")
    public JsonResponse<Object> logout() {
        log.info("123123");
        return loginService.logout();
    }
}
