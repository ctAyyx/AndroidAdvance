package com.ct.framework.jetpack2.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ct.framework.jetpack2.vo.UserInfo

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM userinfo")
    suspend fun getAllUsersNor(): List<UserInfo>

    @Query("SELECT * FROM userinfo")
    fun getAllUsers(): LiveData<List<UserInfo>>

    @Query("SELECT * FROM userinfo WHERE id=:id")
    fun getUserInfo(id: String): LiveData<UserInfo>

    @Query("SELECT * FROM userinfo WHERE id=:id")
    suspend fun getUserInfoBbyNor(id: String): UserInfo

    @Update
    suspend fun upUserInfo(user: UserInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserInfo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: UserInfo)


}