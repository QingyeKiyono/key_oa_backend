package com.key.oa.filter

import com.key.oa.domain.LoginEmployee
import com.key.oa.util.JwtUtil
import com.key.oa.util.RedisUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

@Slf4j
@Component
class JwtAuthenticationFilter @Autowired constructor(
    val jwtUtil: JwtUtil,
    val redisUtil: RedisUtil,
    @Qualifier("handlerExceptionResolver") val resolver: HandlerExceptionResolver,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 获取token
        val token: String? = request.getHeader("token")
        if (token.isNullOrEmpty()) {
            // 这里放行是因为没有token可以解析，在后面的filter中也会无法通过
            // 或者是Spring Boot Actuator相关的监控接口
            filterChain.doFilter(request, response)
            return
        }

        // 解析token中的jobNumber
        val jobNumber = try {
            val claims = jwtUtil.parse(token)
            claims.subject
        } catch (e: Exception) {
            resolver.resolveException(request, response, null, e)
            return
        }

        // 从redis中读取用户信息
        val key = "login:$jobNumber"
        val loginEmployee: LoginEmployee? = redisUtil.getValue(key) as LoginEmployee?

        // 获取权限信息
        // 将用户信息存入SecurityContextHolder
        if (loginEmployee !== null) {
            val usernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken(loginEmployee, null, loginEmployee.authorities)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
        }

        filterChain.doFilter(request, response)
    }
}