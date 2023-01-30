package com.key.oa.config

import com.key.oa.common.JsonResponse
import com.key.oa.common.JsonResponse.Companion.error
import com.key.oa.common.PASSWORD_WRONG
import com.key.oa.common.TOKEN_NOT_FOUND
import com.key.oa.common.logger
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomControllerAdvice {
    val log by logger()

    @ExceptionHandler(UsernameNotFoundException::class)
    fun usernameNotFound(exception: UsernameNotFoundException): ResponseEntity<JsonResponse<Unit>> {
        val response: JsonResponse<Unit> = error("A0201", "用户账户不存在")
        return ResponseEntity.ok().body(response)
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun badCredentialsExceptionHandler(exception: BadCredentialsException): ResponseEntity<JsonResponse<Unit>> {
        val response: JsonResponse<Unit> = when (exception.message) {
            PASSWORD_WRONG -> error("A0210", "用户密码错误")
            TOKEN_NOT_FOUND -> error("A0301", "访问未授权")
            else -> {
                if (log.isInfoEnabled) {
                    log.info("Request unauthorized: {}.", exception.message, exception)
                }
                error("A0300", "访问权限异常")
            }
        }
        return ResponseEntity.ok().body(response)
    }

    @ExceptionHandler(MalformedJwtException::class)
    fun malformedJwtExceptionHandler(exception: MalformedJwtException): ResponseEntity<JsonResponse<Unit>> {
        val response: JsonResponse<Unit> = error("A0403", "Token非法")
        return ResponseEntity.ok().body(response)
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun expiredJwtExceptionHandler(exception: ExpiredJwtException): ResponseEntity<JsonResponse<Unit>> {
        val response: JsonResponse<Unit> = error("A0404", "Token已过期")
        return ResponseEntity.ok().body(response)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(exception: IllegalArgumentException): ResponseEntity<JsonResponse<Unit>> {
        if (log.isInfoEnabled) {
            log.info("Request argument is illegal: {}.", exception.message, exception)
        }
        val response: JsonResponse<Unit> = error("A0402", "无效的用户输入")
        return ResponseEntity.ok().body(response)
    }

    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(exception: Exception): ResponseEntity<JsonResponse<String>> {
        log.error("Exception occurred:\n ", exception)
        val response: JsonResponse<String> = error("B0001", "", exception.message ?: "")
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }
}