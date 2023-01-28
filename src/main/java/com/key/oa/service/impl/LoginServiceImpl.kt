package com.key.oa.service.impl

import com.key.oa.common.JsonResponse
import com.key.oa.common.JsonResponse.Companion.error
import com.key.oa.common.JsonResponse.Companion.success
import com.key.oa.common.TOKEN_NOT_FOUND
import com.key.oa.dto.LoginEmployee
import com.key.oa.dto.LoginDTO
import com.key.oa.service.LoginService
import com.key.oa.util.JwtUtil
import com.key.oa.util.RedisUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*

@Service
class LoginServiceImpl @Autowired constructor(
    private val jwtUtil: JwtUtil,
    private val redisUtil: RedisUtil,
    private val authenticationManager: AuthenticationManager
) : LoginService {
    override fun login(loginDTO: LoginDTO): JsonResponse<String> {
        val authentication = authenticationManager
            .authenticate(UsernamePasswordAuthenticationToken(loginDTO.jobNumber, loginDTO.password))
        // 如果认证没通过，给出对应提示
        if (Objects.isNull(authentication)) {
            return error("A0200", "用户登录异常")
        }
        val loginEmployeeJobNumber = authentication.name

        // 将登陆信息存入redis
        val loginEmployee = authentication.principal as LoginEmployee
        redisUtil.setValue("login:$loginEmployeeJobNumber", loginEmployee)

        // 生成Jwt，返回给前端
        val token = jwtUtil.generate(loginEmployeeJobNumber)
        return success(token)
    }

    override fun logout(): JsonResponse<Unit> {
        // 获取当前登录的员工信息
        val authentication = SecurityContextHolder.getContext().authentication as UsernamePasswordAuthenticationToken?
            ?: throw BadCredentialsException(TOKEN_NOT_FOUND)
        val loginEmployee = authentication.principal as LoginEmployee

        // 获取key并从redis中删除
        val jobNumber = loginEmployee.username
        val key = "login:$jobNumber"
        redisUtil.deleteValue(key)

        return success()
    }
}