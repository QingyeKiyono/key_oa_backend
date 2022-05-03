package com.key.oa.config;

import com.key.oa.common.JsonResponse;
import com.key.oa.common.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 孙强
 * <p>
 * 自定义全局异常处理机制
 */
@Slf4j
@RestControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(UsernameNotFoundException.class)
    public JsonResponse<String> usernameNotFountExceptionHandler(UsernameNotFoundException exception) {
        log.error("Username not fount: {0}", exception);
        JsonResponse<String> jsonResponse = new JsonResponse<>(ResponseInfo.OK);
        jsonResponse.setCode("404");
        jsonResponse.setMessage("Username Not Found");
        jsonResponse.setData(exception.getMessage());
        return jsonResponse;
    }

    @ExceptionHandler(Exception.class)
    public JsonResponse<String> globalExceptionHandler(Exception exception) {
        log.error("Exception occurred: {0}", exception);
        return new JsonResponse<>(ResponseInfo.UNKNOWN_EXCEPTION, exception.getMessage());
    }
}
