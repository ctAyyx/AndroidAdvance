package com.ct.paging3.dto;

import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {

    @SerializedName("data")
    public T data;

}
