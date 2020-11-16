package com.ct.framework.jetpack.net

import androidx.lifecycle.LiveData
import com.ct.framework.jetpack.dto.ApiResponse
import com.ct.framework.jetpack.dto.BaseResponse
import com.ct.framework.jetpack.dto.Category
import com.ct.framework.jetpack.dto.Detail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {


    //https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10
    @GET("/api/v2/data/category/Girl/type/Girl/page/{page}/count/{pageSize}")
    fun getGirlList(
        @Path("page") page: Int,
        @Path("pageSize") pageSize: Int
    ): Call<BaseResponse<List<Category>>>

    //5e777432b8ea09cade05263f
    @GET("/api/v2/post/{id}")
    fun getGirlDetail(@Path("id") id: String): Call<BaseResponse<Detail>>


    /**
     * 下面的接口 用于测试自定义的数据请求模块
     * */

    @GET("/api/v2/post/{id}")
    fun getGirlDetail02(@Path("id") id: String): LiveData<ApiResponse<BaseResponse<Detail>>>

    @GET("/api/v2/post/{id}")
    fun getGirlDetail03(@Path("id") id: String): LiveData<ApiResponse<Detail>>
}

