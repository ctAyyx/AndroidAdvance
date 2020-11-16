package com.ct.framework.jetpack.mvvm.paging

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.MediatorLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.ct.framework.jetpack.mvvm.base.AppExecutors


abstract class PagingResource<ResultType, RequestType>(private val appExecutors: AppExecutors) :
    PagedList.BoundaryCallback<ResultType>() {

    fun asLiveData() = loadFromDB().toLiveData(pageSize = 10, boundaryCallback = this)

    private fun fetchRemote() {
        appExecutors.diskIO().execute {
            saveToDB(progressResponse(createCall()))
        }
    }

    @MainThread
    abstract fun shouldFetchFromRemote(itemAtEnd: ResultType): Boolean

    @MainThread
    abstract fun loadFromDB(): DataSource.Factory<Int, ResultType>

    @WorkerThread
    abstract fun createCall(): RequestType

    @WorkerThread
    abstract fun progressResponse(response: RequestType): List<ResultType>

    @WorkerThread
    abstract fun saveToDB(response: List<ResultType>)

    override fun onItemAtEndLoaded(itemAtEnd: ResultType) {
        super.onItemAtEndLoaded(itemAtEnd)
        //shouldFetchFromRemote(itemAtEnd)
        Log.e("TAG","最后一个数据:$itemAtEnd")
        fetchRemote()
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        fetchRemote()
    }

    override fun onItemAtFrontLoaded(itemAtFront: ResultType) {
        super.onItemAtFrontLoaded(itemAtFront)
        //TODO 这里我们是否可以验证数据的有效性???
    }
}