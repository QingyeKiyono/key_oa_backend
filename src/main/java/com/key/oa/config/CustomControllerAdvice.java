package com.key.oa.config;

import com.key.oa.common.JsonResponse;
import com.key.oa.common.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
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
    @ExceptionHandler(Exception.class)
    public JsonResponse<String> globalExceptionHandler(Exception exception) {
        log.error("Exception occurred: {0}", exception);
        return new JsonResponse<>(ResponseInfo.UNKNOWN_EXCEPTION, exception.getMessage());
    }
}
