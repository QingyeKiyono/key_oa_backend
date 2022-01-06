package com.key.oa.common;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 孙强
 * 用于封装响应值，使其拥有固定的格式
 */
@Getter
@Setter
public class JsonResponse<T> {
    /**
     * 状态码
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

    public JsonResponse(ResponseInfo responseInfo) {
        this.code = responseInfo.getCode();
        this.message = responseInfo.getMessage();
        this.data = null;
    }

    public JsonResponse(ResponseInfo responseInfo, T data) {
        this.code = responseInfo.getCode();
        this.message = responseInfo.getMessage();
        this.data = data;
    }
}
