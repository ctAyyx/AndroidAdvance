package com.ct.framework.jetpack2.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.ct.framework.jetpack2.base.over.DBAndNetSource
import com.ct.framework.jetpack2.base.over.FinalFetchResource
import com.ct.framework.jetpack2.db.dao.UserInfoDao
import com.ct.framework.jetpack2.fetch.BaseResponse
import com.ct.framework.jetpack2.vo.UserInfo
import kotlinx.coroutines.delay
import java.io.Closeable

class UserInfoRepository
    (
    private val dao: UserInfoDao
) : Closeable {


    fun getUserInfoByLiveData(id: String) = object : DBAndNetSource<UserInfo, UserInfo>() {
        override suspend fun createCall(): BaseResponse<UserInfo> {
            delay(10_000L)
            return BaseResponse(UserInfo(id, "网络数据", 30))
        }

        override fun processResponse(response: BaseResponse<UserInfo>): UserInfo {
            return response.data
        }

        override fun loadFromDb(): LiveData<UserInfo> {
            return dao.getUserInfo(id)

        }

        override fun shouldFetch(data: UserInfo?): Boolean {
            return true
        }

        override suspend fun saveCallResult(data: UserInfo) {
            // dao.upUserInfo(data)
            dao.insertUser(data)
        }

    }

    override fun close() {
        Log.e("TAG", "被调用了Close")
    }


}