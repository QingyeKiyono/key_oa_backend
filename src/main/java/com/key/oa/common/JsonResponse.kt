package com.key.oa.common

class JsonResponse<T> private constructor(val code: String, val message: String, val data: T?) {
    companion object {
        @JvmStatic
        fun <T> success(): JsonResponse<T> {
            return JsonResponse("00000", "OK", null)
        }

        @JvmStatic
        fun <T> success(data: T): JsonResponse<T> {
            return JsonResponse("00000", "OK", data)
        }

        @JvmStatic
        fun <T> error(code: String, message: String): JsonResponse<T> {
            return JsonResponse(code, message, null)
        }

        @JvmStatic
        fun <T> error(code: String, message: String, data: T): JsonResponse<T> {
            return JsonResponse(code, message, data)
        }
    }
}