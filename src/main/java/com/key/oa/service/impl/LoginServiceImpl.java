package com.key.oa.service.impl;

import com.key.oa.common.BadCredentialsMessage;
import com.key.oa.common.JsonResponse;
import com.key.oa.domain.LoginEmployee;
import com.key.oa.dto.LoginDTO;
import com.key.oa.service.LoginService;
import com.key.oa.util.JwtUtil;
import com.key.oa.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final RedisUtil redisUtil;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, RedisUtil redisUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
    }

    @Override
    public JsonResponse<String> login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginDTO.getJobNumber(), loginDTO.getPassword()));
        // 如果认证没通过，给出对应提示
        if (Objects.isNull(authentication)) {
            return JsonResponse.error("A0200", "用户登录异常");
        }
        String loginEmployeeJobNumber = authentication.getName();
        log.info("员工: %s 成功登录".formatted(loginEmployeeJobNumber));

        // 将登陆信息存入redis
        LoginEmployee loginEmployee = (LoginEmployee) authentication.getPrincipal();
        redisUtil.setKeyValue("login:" + loginEmployeeJobNumber, loginEmployee);

        // 生成Jwt，返回给前端
        String token = jwtUtil.generate(loginEmployeeJobNumber);
        return JsonResponse.success(token);
    }

    @Override
    public JsonResponse<Void> logout() {
        // 获取当前登录的员工信息
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadCredentialsException(BadCredentialsMessage.TOKEN_NOT_FOUND);
        }
        LoginEmployee loginEmployee = (LoginEmployee) authentication.getPrincipal();

        // 获取key并从redis中删除
        String jobNumber = loginEmployee.getUsername();
        String key = "login:" + jobNumber;
        redisUtil.deleteValue(key);

        log.info("员工: %s 成功注销".formatted(jobNumber));

        return JsonResponse.success();
    }
}
