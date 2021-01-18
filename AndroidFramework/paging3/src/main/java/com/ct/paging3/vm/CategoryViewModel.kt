package com.ct.paging3.vm

import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.ct.paging3.db.CategoryDao
import com.ct.paging3.net.ServiceApi
import com.ct.paging3.source.ExamplePagingSource
import com.ct.paging3.source.ExampleRemoteMediator

class CategoryViewModel(private val serviceApi: ServiceApi, private val dao: CategoryDao) :
    ViewModel() {

    val flow = Pager(
        PagingConfig(pageSize = 10)
    ) {
        ExamplePagingSource(serviceApi)
    }.liveData




    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(
        config = PagingConfig(pageSize = 10),
        initialKey = 0,
        remoteMediator = ExampleRemoteMediator(serviceApi, dao)
    ) {
        dao.pagingSource()
    }.liveData



}