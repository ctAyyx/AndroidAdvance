package com.ct.framework.jetpack.mvvm.paging.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.paging.PagedList
import com.ct.framework.jetpack.dto.Detail
import com.ct.framework.jetpack.mvvm.paging.Resource02
import com.ct.framework.jetpack.room.dao.GirlDao
import com.ct.framework.jetpack.vo.GirlList

class Test2ViewModel(val repository: Test2Repository) : ViewModel() {

    private val _id = MutableLiveData<String>()

//    val girlDetail:Resource02<Detail> = _id.switchMap {
//        repository
//    }

    fun getGirlList(): Resource02<PagedList<GirlList>> = repository.getGirlList()
    fun getGirlListByDB(dao: GirlDao): Resource02<PagedList<GirlList>> = repository.getGirlListByDB2(dao)

}


class Test2VMFactory(private val repository: Test2Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return Test2ViewModel(repository) as T
    }
}