package oa.config

import oa.common.JsonResponse
import oa.common.JsonResponse.Companion.error
import oa.common.PASSWORD_WRONG
import oa.common.TOKEN_NOT_FOUND
import oa.common.logger
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@ResponseBody
@RestControllerAdvice
class CustomControllerAdvice {
    val log by logger()

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(UsernameNotFoundException::class)
    fun usernameNotFound(exception: UsernameNotFoundException): JsonResponse<Unit> {
        return error("A0201", "用户账户不存在")
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BadCredentialsException::class)
    fun badCredentialsExceptionHandler(exception: BadCredentialsException): JsonResponse<Unit> {
        return when (exception.message) {
            PASSWORD_WRONG -> error("A0210", "用户密码错误")
            TOKEN_NOT_FOUND -> error("A0301", "访问未授权")
            else -> {
                if (log.isInfoEnabled) {
                    log.info("Request unauthorized: {}.", exception.message, exception)
                }
                error("A0300", "访问权限异常")
            }
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MalformedJwtException::class)
    fun malformedJwtExceptionHandler(exception: MalformedJwtException): JsonResponse<Unit> {
        return error("A0403", "Token非法")
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ExpiredJwtException::class)
    fun expiredJwtExceptionHandler(exception: ExpiredJwtException): JsonResponse<Unit> {
        return error("A0404", "Token已过期")
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IllegalArgumentException::class)
    fun illegalArgumentExceptionHandler(exception: IllegalArgumentException): JsonResponse<Unit> {
        if (log.isInfoEnabled) {
            log.info("Request argument is illegal: {}.", exception.message, exception)
        }
        return error("A0402", "无效的用户输入")
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun globalExceptionHandler(exception: Exception): JsonResponse<String> {
        log.error("Exception occurred:\n ", exception)
        return error("B0001", "", exception.message ?: "")
    }
}