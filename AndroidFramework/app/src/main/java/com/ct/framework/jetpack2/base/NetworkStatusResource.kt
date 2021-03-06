package com.ct.framework.jetpack2.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * 将 数据模型 和 数据请求状态分离
 * */
abstract class NetworkStatusResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {
    //采用MediatorLiveData
    //可以 同时观察 数据库数据 和 网络数据
    private val result = MediatorLiveData<ResultType>()

    val status = MutableLiveData<NetworkState>()

    var retry: (() -> Unit)? = null

    fun retryAllFail() {
        val fail = retry
        fail?.invoke()
        retry = null
    }

    init {
        toInit()
    }

    private fun toInit() {
        if (useDB())
            initDBAndNet()
        else
            initOnlyNetwork()
    }

    private fun initOnlyNetwork() {
        //result.value = Resource.loading(null)
        setStatus(NetworkState.LOADING)
        toFetchFromNetwork()
    }

    private fun initDBAndNet() {
        //result.value = Resource.loading(null)
        setStatus(NetworkState.LOADING)
        val dbSource = loadFromDb()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data))
                toFetchFromNetwork(dbSource)
            else
                result.addSource(dbSource) { newData ->
                    setValue(newData)
                }

        }
    }

    private fun toFetchFromNetwork(dbSource: LiveData<ResultType>? = null) {
        if (dbSource != null)
            fetchFromNetwork(dbSource)
        else
            fetchOnlyNetwork()
    }

    private fun fetchOnlyNetwork() {
        val apiSource = createCall()
        result.addSource(apiSource) { response ->
            result.removeSource(apiSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        try {
                            Log.e("TAG", "----->$response")
                            val data = processResponse(response)
                            appExecutors.mainThread().execute {
                                setValue(data)
                                setStatus(NetworkState.SUCCESS)
                            }
                        } catch (e: Exception) {
                            Log.e("TAG", "异常:${e.message}")
                            setStatus(NetworkState.error(e.message))

                        }
                    }

                }
                is ApiEmptyResponse -> {
                    setValue(null)
                    setStatus(NetworkState.SUCCESS)
                }
                is ApiErrorResponse -> {
                    setValue(null)
                    retry = {
                        fetchOnlyNetwork()
                    }
                    setStatus(NetworkState.error(response.errorMessage))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiSource = createCall()
        //将数据库作为单一可信来源
        result.addSource(dbSource) { newData ->
            setValue(newData)
        }

        result.addSource(apiSource) { response ->
            result.removeSource(apiSource)
            result.removeSource(dbSource)
            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))
                        //这里我们需要使用新的数据源
                        //如果使用以前的数据源 可能会得到缓存的数据
                        //而这些数据并不一定都是最新的网络数据
                        appExecutors.mainThread().execute {
                            result.addSource(loadFromDb()) { newData ->
                                setStatus(NetworkState.SUCCESS)
                                setValue(newData)
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDb()) { newData ->
                            setStatus(NetworkState.SUCCESS)
                            setValue(newData)

                        }
                    }
                }
                is ApiErrorResponse -> {
                    result.addSource(dbSource) { newData ->
                        setStatus(NetworkState.error(response.errorMessage))
                        setValue(newData)
                    }
                }
            }
        }
    }


    private fun setValue(newValue: ResultType?) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun setStatus(status: NetworkState) {
        if (this.status.value == status)
            return
        this.status.value = status
    }

    fun asLiveData() = result as LiveData<ResultType>

    /**
     * 从网络加载数据
     * */
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    /**
     * 数据转化
     * */
    @WorkerThread
    protected abstract fun processResponse(response: ApiSuccessResponse<RequestType>): ResultType

    /**
     * 是否使用数据库存储
     * */
    protected open fun useDB(): Boolean = false

    /**
     * 从数据库加载数据
     * */
    @MainThread
    protected open fun loadFromDb(): LiveData<ResultType> {
        throw IllegalStateException("If use DB,You should override this method")
    }

    /**
     * 判断是否需要重新从网络获取新的数据
     * */
    @MainThread
    protected open fun shouldFetch(data: ResultType?): Boolean {
        throw IllegalStateException("If use DB,You should override this method")
    }


    /**
     * 将网络数据缓存到数据库
     * */
    @WorkerThread
    protected open fun saveCallResult(data: ResultType) {
        throw IllegalStateException("If use DB,You should override this method")
    }


    /**
     * 当网络数据请求失败了
     * */
    @MainThread
    protected open fun onFetchFail() {
    }

}