package com.ct.framework.adapter.datasource

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.lifecycle.LiveData
import androidx.paging.*
import java.util.concurrent.Executor

/**
 * @ClassName : Extension
 * @CreateDate : 2020/4/2 16:15
 * @Author : CT
 * @Description :
 *
 */

fun <Key, Value> DataSource.Factory<Key, Value>.toPagedLiveData(
    pageSize: Int= 10,
    initialLoadKey: Key? = null,
    boundaryCallback: PagedList.BoundaryCallback<Value>? = null,
    @SuppressLint("RestrictedApi")
    fetchExecutor: Executor = ArchTaskExecutor.getIOThreadExecutor()
): LiveData<PagedList<Value>> {
    return LivePagedListBuilder(this, Config(pageSize = pageSize, initialLoadSizeHint = pageSize))
        .setInitialLoadKey(initialLoadKey)
        .setBoundaryCallback(boundaryCallback)
        .setFetchExecutor(fetchExecutor)
        .build()
}
