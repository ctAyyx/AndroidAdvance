package com.ct.framework.jetpack2.vm

import androidx.lifecycle.ViewModel
import com.ct.framework.jetpack2.base.ResourceStatus
import com.ct.framework.jetpack2.repository.UserInfoRepository
import com.ct.framework.jetpack2.vo.UserInfo
import java.io.Closeable

class UserInfoViewModel(private val repository: UserInfoRepository) : ViewModel() {

    private val list = mutableListOf<Closeable>()

    fun getUserInfoByNet(id: String): ResourceStatus<UserInfo> {
        val request = repository.getUserInfoByLiveData(id)
        list.add(request)

        return request.asResourceStatus()
    }


    override fun onCleared() {
        super.onCleared()
        list.forEach {
            it.close()
        }
        list.clear()
    }
}