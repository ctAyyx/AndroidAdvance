package com.ct.framework.jetpack2.fetch

import androidx.lifecycle.LiveData
import com.ct.framework.jetpack2.base.ApiResponse
import com.ct.framework.jetpack2.dto.Detail
import retrofit2.http.GET
import retrofit2.http.Path

interface GnakIoServiceApi {

    @GET("/api/v2/post/{id}")
    fun getDetail(@Path("id") id: String): LiveData<ApiResponse<BaseResponse<Detail>>>




    @GET("/api/v2/post/{id}")
    suspend fun getDetail02(@Path("id")id:String):BaseResponse<Detail>
}