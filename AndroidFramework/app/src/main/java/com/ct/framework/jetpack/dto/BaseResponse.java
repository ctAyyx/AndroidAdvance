package com.ct.framework.jetpack.dto;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("data")
    public T data;

}
