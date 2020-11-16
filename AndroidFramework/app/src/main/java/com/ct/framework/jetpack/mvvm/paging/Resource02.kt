package com.ct.framework.jetpack.mvvm.paging

import androidx.lifecycle.LiveData

/**
 * 一个保存数据请求的泛型类
 * */
class Resource02<T>(
    val data: LiveData<T>?, //数据
    val networkState: LiveData<NetworkState>? = null, //网络链接状态
    val refreshState: LiveData<NetworkState>? = null, //刷新状态
    val refresh: (() -> Unit) = {}, //刷新数据
    val retry: (() -> Unit) = {} //重试
) {

    companion object {
        //
//        fun <T> success(data: T?): Resource02<T> {
//            return Resource02(Status.SUCCESS, data, null)
//        }
//
//        fun <T> error(msg: String, data: T?): Resource02<T> {
//            return Resource02(Status.ERROR, data, msg)
//        }
//
        fun <T> loading(
            data: LiveData<T>?,
            networkState: LiveData<NetworkState>?,
            refreshState: LiveData<NetworkState>?
        ): Resource02<T> {
            return Resource02(
                data = data,
                networkState = networkState,
                refreshState = refreshState
            )
        }
    }
}