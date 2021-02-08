package com.ct.framework.jetpack2.base.over

import androidx.lifecycle.LiveData
import com.ct.framework.jetpack2.base.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class DBAndNetSource<ResultType, RequestType> :
    BaseFetchResource<ResultType, RequestType>() {

    override suspend fun fetch() {
        setStatus(NetworkState.LOADING)
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                setValue(data)
                launch { fetchFromNetwork(dbSource) }
            } else
                result.addSource(dbSource) { newData ->
                    setValue(newData)
                }

        }
    }

    private suspend fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiSource = createCall()
        //将数据库作为单一可信来源
        withContext(Dispatchers.Main) {
            result.addSource(dbSource) { newData ->
                setValue(newData)
            }
        }
        try {
            saveCallResult(processResponse(apiSource))
            setStatus(NetworkState.SUCCESS)
        } catch (e: Exception) {
            retry = {
                fetchFromNetwork(dbSource)
            }
            setStatus(NetworkState.error(e.message ?: "Unknown Error"))
        }


    }


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
    protected abstract suspend fun saveCallResult(data: ResultType?)

}