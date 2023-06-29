package com.ct.framework.jetpack.dto

import com.google.gson.annotations.SerializedName

data class BaseResponse2<T>(
    @SerializedName("data")
    val data: T,
    @SerializedName("errorCode")
    val errorCode: Int,
    @SerializedName("errorMsg")
    val errorMsg: String?
)