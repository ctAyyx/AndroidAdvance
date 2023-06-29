package com.ct.framework.jetpack.net

import androidx.lifecycle.LiveData
import com.ct.framework.jetpack.dto.ApiResponse
import com.ct.framework.jetpack.dto.BaseResponse2
import com.ct.framework.jetpack.dto.ChapterParent
import retrofit2.http.GET
import retrofit2.http.Path

interface WanAndroidApi {

    @GET("article/list/{page}/json")
    fun getChapterList(@Path("page") page: Int): LiveData<ApiResponse<BaseResponse2<ChapterParent>>>
}