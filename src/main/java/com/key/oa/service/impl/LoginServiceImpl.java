package com.key.oa.service.impl;

import com.key.oa.common.JsonResponse;
import com.key.oa.common.ResponseInfo;
import com.key.oa.dto.LoginDTO;
import com.key.oa.service.LoginService;
import com.key.oa.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 孙强
 * 登录服务的实现类
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public JsonResponse<String> login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDTO.getJobNumber(), loginDTO.getPassword()));
        // 如果认证没通过，给出对应提示
        if (Objects.isNull(authentication)) {
            return new JsonResponse<>(ResponseInfo.EMPLOYEE_NOT_FOUND, "登录失败");
        }

        log.info("员工: %s 成功登录".formatted(authentication.getName()));
        // 认证通过，生成Jwt，传入对象
        String token = jwtUtil.generate(authentication.getName());

        return new JsonResponse<>(ResponseInfo.OK, token);
    }
}
