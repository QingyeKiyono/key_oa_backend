package com.key.oa.common;

/**
 * @author 孙强
 * 将JsonResponse中的code和message对应起来
 */
public enum ResponseInfo {

    /**
     * 没有异常，程序正常返回
     */
    OK("000");

    private final String code;

    ResponseInfo(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.name();
    }
}