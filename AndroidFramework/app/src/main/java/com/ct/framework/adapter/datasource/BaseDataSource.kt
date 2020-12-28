package com.ct.framework.adapter.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import com.ct.framework.jetpack2.base.NetworkState
import java.net.UnknownHostException

/**
 * 根据Key加载下一页数据
 * */
abstract class BasePageKeyedDataSource<Key, Value> : PageKeyedDataSource<Key, Value>() {
    //表示网络请求状态
    val networkState = MutableLiveData<NetworkState>()
    //表示初始化和刷新状态
    val initialLoad = MutableLiveData<NetworkState>()

    //重试
    private var retry: (() -> Any)? = null

    init {
        addInvalidatedCallback {
            onInvalidated()
        }
    }


    abstract fun loadInit(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, Value>
    )

    abstract fun loadMore(params: LoadParams<Key>, callback: LoadCallback<Key, Value>)

    /**
     *在数据源失效时调用
     * */
    open fun onInvalidated() {}


    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadInitial(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Key, Value>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        try {
            loadInit(params, callback)
            retry = null
            networkState.postValue(NetworkState.SUCCESS)
            initialLoad.postValue(NetworkState.SUCCESS)
        } catch (e: Exception) {
            retry = {
                loadInitial(params, callback)
            }
            val error =
                if (e is UnknownHostException) NetworkState.error("当前网络不可用")
                else NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
            initialLoad.postValue(error)
            e.printStackTrace()
        }

    }


    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
        networkState.postValue(NetworkState.LOADING)
        try {
            loadMore(params, callback)
            retry = null
            networkState.postValue(NetworkState.SUCCESS)
        } catch (e: Exception) {
            retry = {
                loadAfter(params, callback)
            }
            val error =
                if (e is UnknownHostException) NetworkState.error("当前网络不可用")
                else NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
            e.printStackTrace()
        }
    }


    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Key, Value>) {
    }


}

/**
 * 根据最后一个item来加载下一页数据
 * */
abstract class BaseItemKeyedDataSource<Key, Value> : ItemKeyedDataSource<Key, Value>() {

    //表示网络请求状态
    val networkState = MutableLiveData<NetworkState>()
    //表示初始化和刷新状态
    val initialLoad = MutableLiveData<NetworkState>()

    init {
        addInvalidatedCallback { }
    }

    init {
        addInvalidatedCallback {
            onInvalidated()
        }
    }

    abstract fun loadInit(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Value>
    )

    abstract fun loadMore(params: LoadParams<Key>, callback: LoadCallback<Value>)

    /**
     *在数据源失效时调用
     * */
    open fun onInvalidated() {}


    override fun loadInitial(
        params: LoadInitialParams<Key>,
        callback: LoadInitialCallback<Value>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        try {
            loadInit(params, callback)
            networkState.postValue(NetworkState.SUCCESS)
            initialLoad.postValue(NetworkState.SUCCESS)
        } catch (e: Exception) {
            val error =
                if (e is UnknownHostException) NetworkState.error("当前网络不可用")
                else NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Value>) {
        networkState.postValue(NetworkState.LOADING)
        try {
            loadMore(params, callback)
            networkState.postValue(NetworkState.SUCCESS)
        } catch (e: Exception) {
            val error =
                if (e is UnknownHostException) NetworkState.error("当前网络不可用")
                else NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
        }
    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Value>) {

    }

}

abstract class BasePositionDataSource<Value> : PositionalDataSource<Value>() {
    val networkState = MutableLiveData<NetworkState>()
    val initialLoad = MutableLiveData<NetworkState>()

    init {
        addInvalidatedCallback {
            onInvalidated()
        }
    }


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Value>) {
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Value>) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)
        try {

            loadInit(params, callback)
            networkState.postValue(NetworkState.SUCCESS)
            initialLoad.postValue(NetworkState.SUCCESS)
        } catch (e: Exception) {
            e.printStackTrace()
            val error = NetworkState.error(e.message ?: "请求数据失败")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }

    abstract fun loadInit(params: LoadInitialParams, callback: LoadInitialCallback<Value>)
    /**
     *在数据源失效时调用
     * */
    open fun onInvalidated() {}
}