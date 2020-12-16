package com.ct.framework.jetpack2.base

import androidx.lifecycle.LiveData

/**
 * 拥有请求状态的数据类
 * */
data class ResourceStatus<T>(
    val data: LiveData<T>?, //数据
    val networkState: LiveData<NetworkState>? = null, //网络链接状态
    val refreshState: LiveData<NetworkState>? = null, //刷新状态
    val refresh: (() -> Unit) = {}, //刷新数据
    val retry: (() -> Unit) = {} //重试

)

