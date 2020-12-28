package com.ct.paging3.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ct.paging3.db.CategoryDao
import com.ct.paging3.net.ServiceApi

class CategoryViewModelFactory(private val serviceApi: ServiceApi, private val dao: CategoryDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(serviceApi, dao) as T
    }
}