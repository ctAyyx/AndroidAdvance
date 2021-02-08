package com.ct.framework.jetpack2.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ct.framework.jetpack2.repository.GankIoRepository
import com.ct.framework.jetpack2.repository.UserInfoRepository

class UserInfoViewModelFactory(private val repository: UserInfoRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val vm = UserInfoViewModel(repository)
        return vm as T
    }
}