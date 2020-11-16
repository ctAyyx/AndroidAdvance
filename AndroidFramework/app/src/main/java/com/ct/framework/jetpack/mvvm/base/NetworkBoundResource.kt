package com.ct.framework.jetpack.mvvm.base

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ct.framework.jetpack.dto.ApiEmptyResponse
import com.ct.framework.jetpack.dto.ApiErrorResponse
import com.ct.framework.jetpack.dto.ApiResponse
import com.ct.framework.jetpack.dto.ApiSuccessResponse

/**
 * Repository数据请求层
 * ResultType : vo数据类型 (界面数据类型)
 * RequestType: dto数据类型(传输数据类型)
 *
 * 这个适用于普通的网络请求 如果引入了Paging库 则无法使用了
 * */
abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {
    //采用MediatorLiveData
    //可以 同时观察 数据库数据 和 网络数据
    private val result = MediatorLiveData<Resource<ResultType>>()

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
        result.value = Resource.loading(null)
        toFetchFromNetwork()
    }

    private fun initDBAndNet() {
        result.value = Resource.loading(null)

        val dbSource = loadFromDb()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data))
                toFetchFromNetwork(dbSource)
            else
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
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
                                setValue(Resource.success(data))
                            }
                        } catch (e: Exception) {
                            Log.e("TAG", "异常:${e.message}")
                        }
                    }

                }
                is ApiEmptyResponse -> {
                    setValue(Resource.success(null))
                }
                is ApiErrorResponse -> {
                    setValue(Resource.error(response.errorMessage, null))
                }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiSource = createCall()
        //将数据库作为单一可信来源
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
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
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))

                        }
                    }
                }
                is ApiErrorResponse -> {
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }


    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

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