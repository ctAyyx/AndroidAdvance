package com.ct.framework.jetpack2.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.ct.framework.jetpack2.fetch.BaseResponse
import kotlinx.coroutines.*

/**
 * 将 数据模型 和 数据请求状态分离
 *
 * 这里 我们引入协程
 * */
abstract class NetworkStatusWithCoroutinesResource<ResultType, RequestType> {
    //采用MediatorLiveData
    //可以 同时观察 数据库数据 和 网络数据
    private val result = MediatorLiveData<ResultType>()

    val status = MutableLiveData<NetworkState>()

    var retry: (suspend () -> Unit)? = null

    suspend fun retryAllFail() {
        val fail = retry
        fail?.invoke()
        retry = null
    }

    private suspend fun toInit() {
        if (useDB())
            initDBAndNet()
        else
            initOnlyNetwork()
    }

    private suspend fun initOnlyNetwork() {
        setStatus(NetworkState.LOADING)
        toFetchFromNetwork()
    }

    private suspend fun initDBAndNet() {
        setStatus(NetworkState.LOADING)
        val dbSource = loadFromDb()
        result.postValue(dbSource)
        if (shouldFetch(dbSource))
            fetchFromNetwork2()
        else {
            setStatus(NetworkState.SUCCESS)
        }

    }

    private suspend fun toFetchFromNetwork(dbSource: LiveData<ResultType>? = null) {
        if (dbSource != null)
            fetchFromNetwork2()
        else
            fetchOnlyNetwork()
    }

    private suspend fun fetchOnlyNetwork() {
        try {
            val apiSource = createCall()
            setValue(processResponse(apiSource))
            setStatus(NetworkState.SUCCESS)
        } catch (e: Exception) {
            retry = {
                fetchOnlyNetwork()
            }
            setValue(null)
            setStatus(NetworkState.error(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun fetchFromNetwork2() {

        try {
            val apiSource = createCall()
            saveCallResult(processResponse(apiSource))
            result.postValue(loadFromDb())
            setStatus(NetworkState.SUCCESS)
        } catch (e: Exception) {
            retry = {
                fetchOnlyNetwork()
            }
            result.postValue(loadFromDb())
            setStatus(NetworkState.error(e.message ?: "Unknown Error"))
        }
    }


    private fun setValue(newValue: ResultType?) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    private fun setStatus(status: NetworkState) {
        if (this.status.value == status)
            return
        this.status.postValue(status)
    }

    fun asLiveData() = liveData<ResultType> {
        toInit()
        emitSource(result)
    }

    /**
     * 从网络加载数据
     * */
    protected abstract suspend fun createCall(): BaseResponse<RequestType>

    /**
     * 数据转化
     * */
    protected abstract fun processResponse(response: BaseResponse<RequestType>): ResultType

    /**
     * 是否使用数据库存储
     * */
    protected open fun useDB(): Boolean = false

    /**
     * 从数据库加载数据
     * */
    protected open suspend fun loadFromDb(): ResultType {
        throw IllegalStateException("If use DB,You should override this method")
    }

    /**
     * 判断是否需要重新从网络获取新的数据
     * */
    protected open fun shouldFetch(data: ResultType?): Boolean {
        throw IllegalStateException("If use DB,You should override this method")
    }


    /**
     * 将网络数据缓存到数据库
     * */
    protected open suspend fun saveCallResult(data: ResultType) {
        throw IllegalStateException("If use DB,You should override this method")
    }

}