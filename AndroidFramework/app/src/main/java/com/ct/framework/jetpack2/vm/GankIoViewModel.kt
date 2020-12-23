package com.ct.framework.jetpack2.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ct.framework.jetpack2.repository.GankIoRepository

/**
 * GankIo ViewModel
 * */
class GankIoViewModel(private val repository: GankIoRepository) : ViewModel() {

    fun getGirlDetail(id: String) = repository.getDetail(id = id)

    fun getGirlDetailWithStatus(id: String) = repository.getDetailWithStatus(id = id)

    fun getDetailWithCoroutines(id: String) = repository.getDetailWithCoroutines(id)

    fun a(){
        viewModelScope
    }
}

