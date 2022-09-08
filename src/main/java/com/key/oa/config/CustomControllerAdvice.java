package com.key.oa.config;

import com.key.oa.common.BadCredentialsMessage;
import com.key.oa.common.JsonResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义全局异常处理机制
 *
 * @author 孙强
 */
@Slf4j
@RestControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<JsonResponse<Void>> usernameNotFountExceptionHandler(UsernameNotFoundException exception) {
        if (log.isInfoEnabled()) {
            log.info("Username not found: ", exception);
        }

        JsonResponse<Void> response = JsonResponse.error("A0201", "用户账户不存在");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<JsonResponse<Void>> badCredentialsExceptionHandler(BadCredentialsException exception) {
        JsonResponse<Void> response;

        if (BadCredentialsMessage.PASSWORD_WRONG.equals(exception.getMessage())) {
            if (log.isInfoEnabled()) {
                log.info("Password wrong.");
            }

            response = JsonResponse.error("A0210", "用户密码错误");
        } else if (BadCredentialsMessage.TOKEN_NOT_FOUND.equals(exception.getMessage())) {
            if (log.isInfoEnabled()) {
                log.info("Token not found.");
            }

            response = JsonResponse.error("A0301", "访问未授权");
        } else {
            if (log.isInfoEnabled()) {
                log.info("Request unauthorized.");
            }

            response = JsonResponse.error("A0300", "访问权限异常");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<JsonResponse<Void>> malformedJwtExceptionHandler(MalformedJwtException exception) {
        if (log.isInfoEnabled()) {
            log.info("Token is malformed: {}.", exception.getMessage());
        }

        JsonResponse<Void> response = JsonResponse.error("A0403", "Token非法");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<JsonResponse<Void>> expiredJwtExceptionHandler(ExpiredJwtException exception) {
        if (log.isInfoEnabled()) {
            log.info("Token expired: {}.", exception.getMessage());
        }

        JsonResponse<Void> response = JsonResponse.error("A0404", "Token已过期");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<JsonResponse<Void>> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        if (log.isInfoEnabled()) {
            log.info("Request argument is illegal: {}.", exception.getMessage());
        }

        JsonResponse<Void> response = JsonResponse.error("A0402", "无效的用户输入");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResponse<String>> globalExceptionHandler(Exception exception) {
        log.error("Exception occurred: ", exception);

        JsonResponse<String> response = JsonResponse.error("B0001", "系统执行出错", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
