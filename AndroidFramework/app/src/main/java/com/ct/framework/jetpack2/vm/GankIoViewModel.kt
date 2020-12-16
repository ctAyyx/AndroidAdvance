package com.ct.framework.jetpack2.vm

import androidx.lifecycle.ViewModel
import com.ct.framework.jetpack2.repository.GankIoRepository

/**
 * GankIo ViewModel
 * */
class GankIoViewModel(private val repository: GankIoRepository) : ViewModel() {

    fun getGirlDetail(id: String) = repository.getDetail(id = id)

    fun getGirlDetailWithStatus(id: String) = repository.getDetailWithStatus(id = id)


}

