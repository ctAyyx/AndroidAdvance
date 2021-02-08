package com.ct.framework.jetpack2.base.over

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.ct.framework.jetpack2.base.NetworkState
import com.ct.framework.jetpack2.base.ResourceStatus
import com.ct.framework.jetpack2.fetch.BaseResponse
import kotlinx.coroutines.*
import java.io.Closeable


abstract class BaseFetchResource<ResultType, RequestType> : Closeable,
    CoroutineScope by MainScope() {

    protected val result = MediatorLiveData<ResultType>()
    private val status = MutableLiveData<NetworkState>()
    protected var retry: (suspend () -> Unit)? = null

    private fun retryAllFail() = launch {
        val fail = retry
        fail?.invoke()
        retry = null
    }

    private fun refresh() = launch {
        fetch()
    }

    protected fun setValue(newValue: ResultType?) = launch(Dispatchers.Main) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    protected fun setStatus(newStatus: NetworkState) = launch(Dispatchers.Main) {
        if (status.value != newStatus)
            status.value = newStatus
    }

    fun asLiveData() = liveData<ResultType> {
        fetch()
        emitSource(result)
    }

    fun asResourceStatus() = ResourceStatus(
        data = asLiveData(),
        networkState = status,
        refreshState = status,
        retry = { retryAllFail() },
        refresh = { refresh() }
    )


    override fun close() {
        cancel()
    }


    /**
     * 开始获取数据
     * */
    abstract suspend fun fetch()

    /**
     * 从网络加载数据
     * 这里 我们适配Retrofit 不再使用LiveData 而是使用挂起函数
     * */
    protected abstract suspend fun createCall(): BaseResponse<RequestType>

    /**
     * 数据转化
     * */
    protected abstract suspend fun processResponse(response: BaseResponse<RequestType>): ResultType?


}