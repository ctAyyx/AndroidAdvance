package com.ct.framework.adapter

import androidx.paging.DataSource

class PagingSimpleDatasourceFactory : DataSource.Factory<Int, AdapterVo>() {
    override fun create(): DataSource<Int, AdapterVo> {
        return PagingSimpleDataSource()
    }
}