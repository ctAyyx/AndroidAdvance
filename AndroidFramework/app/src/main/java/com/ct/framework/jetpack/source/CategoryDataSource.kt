package com.ct.framework.jetpack.source

import androidx.paging.PageKeyedDataSource
import com.ct.framework.jetpack.dto.Category
import com.ct.framework.jetpack.mvvm.base.BasePageKeyedDataSource
import com.ct.framework.jetpack.net.ServiceApi
import com.ct.framework.jetpack.vo.GirlList

class CategoryDataSource(private val serviceApi: ServiceApi) :
    BasePageKeyedDataSource<Int, GirlList>() {


    private fun progressResponse(requestData: List<Category>): List<GirlList> = requestData.map {
        GirlList(it.id, it.title, it.url, it.author)
    }

    override fun loadInit(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GirlList>
    ) {
        val call = serviceApi.getGirlList(1, pageSize = 10)
        val response = call.execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            callback.onResult(progressResponse(body.data), 1, 2)
        } else {
            callback.onError(RuntimeException("失败:${response.errorBody()}"))
        }
    }

    override fun loadMore(params: LoadParams<Int>, callback: LoadCallback<Int, GirlList>) {
        val call = serviceApi.getGirlList(params.key, pageSize = 10)
        val response = call.execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            callback.onResult(progressResponse(body.data), params.key + 1)
        } else {
            callback.onError(RuntimeException("失败:${response.errorBody()}"))
        }
    }
}