package com.ct.framework.jetpack.mvvm.paging

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.ct.framework.jetpack.mvvm.base.AppExecutors

/**
 * Repository数据请求层
 * ResultType : vo数据类型 (界面数据类型)
 * RequestType: dto数据类型(传输数据类型)
 *
 * 为了适应Paging库
 * */
abstract class NetworkBoundResource02<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors){

    //数据请求状态
    val networkState = MutableLiveData<NetworkState>()


    //采用MediatorLiveData
    //可以 同时观察 数据库数据 和 网络数据
    private val result = MediatorLiveData<ResultType>()

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
        networkState.value =
            NetworkState.LOADING
        toFetchFromNetwork()
    }

    private fun initDBAndNet() {
        networkState.value =
            NetworkState.LOADING

        val dbSource = loadFromDb()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data))
                toFetchFromNetwork(dbSource)
            else
                result.addSource(dbSource) { newData ->
                    setValue(newData)
                    networkState.value =
                        NetworkState.SUCCESS
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
            if (response == null) {
                setValue(null)
                networkState.postValue(
                    NetworkState.error(
                        "TODO 怎么来控制异常?"
                    )
                )
            } else {
                setValue(processResponse(response))
                networkState.postValue(NetworkState.SUCCESS)
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
            appExecutors.diskIO().execute {
                saveCallResult(processResponse(response))
                appExecutors.mainThread().execute {
                    result.addSource(loadFromDb()) { newData ->
                        result.postValue(newData)
                        networkState.postValue(NetworkState.SUCCESS)
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

    fun asLiveData() = result as LiveData<ResultType>

    /**
     * 从网络加载数据
     * */
    @MainThread
    protected abstract fun createCall(): LiveData<RequestType>

    /**
     * 数据转化
     * */
    @WorkerThread
    protected abstract fun processResponse(response: RequestType?): ResultType

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