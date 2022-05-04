package com.key.oa.common;

import lombok.Getter;
import lombok.Setter;

/**
 * 用于封装响应值，使其拥有固定的格式
 *
 * @author 孙强
 */
@Getter
@Setter
public class JsonResponse<T> {
    /**
     * 错误码，与HttpStatus无关，参考阿里Java开发手册泰山版
     */
    private String code;

    /**
     * 返回的额外信息
     */
    private String message;

    /**
     * 需要封装的数据
     */
    private T data;

    public JsonResponse() {
        this.code = "00000";
        this.message = "OK";
        this.data = null;
    }

    public JsonResponse(T data) {
        this.code = "00000";
        this.message = "OK";
        this.data = data;
    }

    public JsonResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public JsonResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
