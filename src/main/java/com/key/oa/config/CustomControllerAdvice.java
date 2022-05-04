package com.key.oa.config;

import com.key.oa.common.JsonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        log.error("Username not found: ", exception);

        JsonResponse<Void> response = new JsonResponse<>("A0201", "用户账户不存在");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<JsonResponse<Void>> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        log.error("Illegal argument: ", exception);

        JsonResponse<Void> response = new JsonResponse<>("A0402", "无效的用户输入");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonResponse<String>> globalExceptionHandler(Exception exception) {
        log.error("Exception occurred: ", exception);

        JsonResponse<String> response = new JsonResponse<>("B0001", "系统执行出错", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
