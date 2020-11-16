package com.ct.framework.jetpack.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ct.framework.jetpack.dto.Category
import com.ct.framework.jetpack.net.ServiceApi
import com.ct.framework.jetpack.vo.GirlList


class CategoryDataSourceFactory(private val serviceApi: ServiceApi) :
    DataSource.Factory<Int, GirlList>() {
    val dataSource = MutableLiveData<CategoryDataSource>()
    override fun create(): DataSource<Int, GirlList> {
        val source = CategoryDataSource(serviceApi)
        dataSource.postValue(source)
        return source
    }
}