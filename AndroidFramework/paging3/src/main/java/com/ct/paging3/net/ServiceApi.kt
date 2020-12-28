package com.ct.paging3.net

import com.ct.paging3.dto.BaseResponse
import com.ct.paging3.dto.Category
import com.ct.paging3.dto.Detail
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceApi {


    //https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10
    @GET("/api/v2/data/category/Girl/type/Girl/page/{page}/count/{pageSize}")
    suspend fun getGirlList(
        @Path("page") page: Int,
        @Path("pageSize") pageSize: Int
    ): BaseResponse<List<Category>>

    //5e777432b8ea09cade05263f
    @GET("/api/v2/post/{id}")
    suspend fun getGirlDetail(@Path("id") id: String): BaseResponse<Detail>

}

