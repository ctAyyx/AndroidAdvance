package com.ct.framework.jetpack2.base.over

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.ct.framework.jetpack2.base.NetworkState
import com.ct.framework.jetpack2.fetch.BaseResponse
import kotlinx.coroutines.*
import java.io.Closeable
import kotlin.coroutines.CoroutineContext

/**
 * 将 数据模型 和 数据请求状态分离
 * 这里 我们引入协程
 * */
abstract class FinalFetchResource<ResultType, RequestType> : Closeable {

    private val mHandler = Handler(Looper.getMainLooper())
    private val fetchScope by lazy { FinalFetchCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate) }

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
        initDBAndNet()
    }


    private suspend fun initDBAndNet() {
        setStatus(NetworkState.LOADING)
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                setValue(data)
                fetchScope.launch { fetchFromNetwork(dbSource) }
            } else
                result.addSource(dbSource) { newData ->
                    setValue(newData)
                }

        }


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

    private suspend fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiSource = createCall()
        //将数据库作为单一可信来源
        fetchScope.launch(Dispatchers.Main) {
            result.addSource(dbSource) { newData ->
                setValue(newData)
            }
        }
        try {
            saveCallResult(processResponse(apiSource))
//            fetchScope.launch(Dispatchers.Main) {
//                result.removeSource(dbSource)
//                result.addSource(loadFromDb()) { newData ->
//                    setValue(newData)
//                }
//
//            }
            setStatus(NetworkState.SUCCESS)

        } catch (e: Exception) {
            retry = {
                fetchFromNetwork(dbSource)
            }
            setStatus(NetworkState.error(e.message ?: "Unknown Error"))
        }

    }


    private fun setValue(newValue: ResultType?) {
        if (result.value != newValue) {
            mHandler.post {
                result.value = newValue
            }
        }
    }

    private fun setStatus(status: NetworkState) {
        if (this.status.value == status)
            return
        mHandler.post {
            this.status.value = status
        }
    }

    fun asLiveData() = liveData<ResultType> {
        toInit()
        emitSource(result)
    }

    /**
     * 从网络加载数据
     * 这里 我们适配Retrofit 不再使用LiveData 而是使用挂起函数
     * */
    protected abstract suspend fun createCall(): BaseResponse<RequestType>

    /**
     * 数据转化
     * */
    protected abstract fun processResponse(response: BaseResponse<RequestType>): ResultType


    /**
     * 从数据库加载数据
     *
     * 这里使用LiveData 是因为我们可能需要在数据更新的时候实时更新UI
     * */
    protected abstract fun loadFromDb(): LiveData<ResultType>

    /**
     * 判断是否需要重新从网络获取新的数据
     * */
    protected abstract fun shouldFetch(data: ResultType?): Boolean


    /**
     * 将网络数据缓存到数据库
     * */
    protected abstract suspend fun saveCallResult(data: ResultType)

    override fun close() {
        Log.e("TAG", "取消协程数据")
        fetchScope.cancel()
    }

    internal class FinalFetchCoroutineScope(context: CoroutineContext) : CoroutineScope, Closeable {
        override val coroutineContext: CoroutineContext = context
        override fun close() {
            coroutineContext.cancel()
        }
    }
}



