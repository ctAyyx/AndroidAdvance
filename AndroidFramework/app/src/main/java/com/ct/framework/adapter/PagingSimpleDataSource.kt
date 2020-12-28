package com.ct.framework.adapter

import android.util.Log
import androidx.paging.PageKeyedDataSource

class PagingSimpleDataSource : PageKeyedDataSource<Int, AdapterVo>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, AdapterVo>
    ) {
        Log.e("TAG", "---->初始化数据")
        Thread.sleep(2000L)

        val mList = mutableListOf<AdapterVo>()
        for (i in 0 until 10) {
            mList.add(AdapterVo("分页测试数据1:$i", i))
        }
        callback.onResult(mList, 1, 2)

    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, AdapterVo>) {
        Log.e("TAG", "---->获取更多数据:${params.key}")
        Thread.sleep(2000L)

        val mList = mutableListOf<AdapterVo>()
        val key = params.key
        for (i in 0 until 10) {
            mList.add(AdapterVo("分页测试数据$key:$i", i * key))
        }
        callback.onResult(mList, key + 1)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, AdapterVo>) {
    }

}