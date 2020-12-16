package com.ct.framework.jetpack2.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ct.framework.jetpack2.repository.GankIoRepository

class GankIoViewModelFactory(private val repository: GankIoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val vm = GankIoViewModel(repository)
        return vm as T
    }
}