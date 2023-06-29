package com.ct.framework.jetpack.dto

import retrofit2.Response

/**
 * 该类只处理由网络请求响应的数据
 *
 * 对响应后的数据处理由用户自己实现
 * */
sealed class ApiResponse<T>() {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> =
            ApiErrorResponse(error.message ?: "unknown error", error)

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null)
                    ApiEmptyResponse()
                else
                    ApiSuccessResponse(data = body)
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrBlank())
                    response.message()
                else
                    msg
                ApiErrorResponse(errorMsg ?: "unknown error", NullPointerException())
            }
        }

    }
}

/**
 * 成功的响应数据
 * */
data class ApiSuccessResponse<T>(val data: T) : ApiResponse<T>()

/**
 * 空数据的响应数据
 * */
class ApiEmptyResponse<T> : ApiResponse<T>()

/**
 * 错误的响应数据
 * */
data class ApiErrorResponse<T>(val errorMessage: String, val throwable: Throwable) :
    ApiResponse<T>()