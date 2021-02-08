package com.ct.framework.jetpack2.fetch

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("data")
    val data: T?
)