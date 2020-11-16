package com.ct.framework.jetpack.mvvm.vm

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.ct.framework.jetpack.dto.Category
import com.ct.framework.jetpack.dto.Detail
import com.ct.framework.jetpack.mvvm.base.AppExecutors
import com.ct.framework.jetpack.mvvm.base.Resource
import com.ct.framework.jetpack.mvvm.repository.ListRepository
import kotlinx.coroutines.*

/**
 * ViewModel 对象为特定的界面组件（如 Fragment 或 Activity）提供数据，并包含数据处理业务逻辑，以与模型进行通信。
 * */
class ListViewModel(repository: ListRepository) : ViewModel() {

    private val page: Int = 0
    private val _id = MutableLiveData<String>()


    private val data: LiveData<List<Category>> = repository.getList(page, pageSize = 10)

    val detailData: LiveData<Detail> = _id.switchMap { id ->
        repository.getGirlDetail(id)
    }


    fun setId(id: String) {
        if (_id.value == id)
            return
        _id.value = id
    }


    /**
     * 下面的方法 测试自定义的数据请求
     * */
    val detailData02: LiveData<Resource<Detail>> = _id.switchMap { id ->
        repository.getGirlDetail02(id, AppExecutors())
    }


    val detailData03: LiveData<Resource<Detail>> = _id.switchMap { id ->
        repository.getGirlDetail03(id, AppExecutors())
    }

    val detailData04: LiveData<Resource<Detail>> = _id.switchMap { id ->
        repository.getGirlDetail04(id)
    }


    /**
     * 下面测试 列表数据
     * */


    class ListVMFactory(private val repository: ListRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ListViewModel(repository) as T
        }

    }





}