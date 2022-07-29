package com.key.oa.filter;

import com.key.oa.domain.LoginEmployee;
import com.key.oa.util.JwtUtil;
import com.key.oa.util.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author 孙强
 * Jwt请求拦截器
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    private final RedisUtil redisUtil;

    @Autowired
    public JwtAuthenticationFilter(JwtUtil jwtUtil, RedisUtil redisUtil) {
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            // 这里放行是因为没有token可以解析，在后面的filter中也会无法通过
            // 或者是Spring Boot Actuator相关的监控接口
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token中的JobNumber
        String jobNumber;
        try {
            Claims claims = jwtUtil.parse(token);
            jobNumber = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Token非法");
        }

        // 从redis中获取用户信息
        String key = "login:" + jobNumber;
        LoginEmployee loginEmployee = (LoginEmployee) redisUtil.getValue(key);

        // 获取权限信息
        // 将用户信息存入SecurityContextHolder
        if (Objects.nonNull(loginEmployee)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginEmployee, null, loginEmployee.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        // 放行
        filterChain.doFilter(request, response);
    }
}
