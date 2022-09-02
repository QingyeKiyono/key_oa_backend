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

    private JsonResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> JsonResponse<T> success() {
        return new JsonResponse<>("00000", "OK", null);
    }

    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<>("00000", "OK", data);
    }

    public static <T> JsonResponse<T> error(String code, String message) {
        return new JsonResponse<>(code, message, null);
    }

    public static <T> JsonResponse<T> error(String code, String message, T data) {
        return new JsonResponse<>(code, message, data);
    }
}
