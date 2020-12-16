package com.ct.framework.jetpack2.base


/**
 * 数据请求
 *
 * 用于 适配添加Paging库后 对网络状态的监听
 * */
data class NetworkState private constructor(
    val status: Status,
    val code: Int,
    val msg: String? = ""
) {
    companion object {
        val SUCCESS = NetworkState(
            Status.SUCCESS,
            200
        )
        val LOADING = NetworkState(
            Status.LOADING,
            200
        )

        fun error(msg: String?, code: Int = 0) =
            NetworkState(
                Status.ERROR,
                code,
                msg
            )
    }

}