package com.key.oa.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 孙强
 * 用于封装响应值，使其拥有固定的格式
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse<T> {
    /**
     * 需要封装的数据
     */
    private T data;

    /**
     * 返回的额外信息
     */
    private String message;

    /**
     * 状态码
     */
    private String code;

    public JsonResponse(T data) {
        this.data = data;
        this.code = "0";
        this.message = "";
    }

    public JsonResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }
}
