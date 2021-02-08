package com.ct.framework.jetpack2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ct.framework.jetpack2.db.dao.GanKIoDao
import com.ct.framework.jetpack2.db.dao.UserInfoDao
import com.ct.framework.jetpack2.vo.DetailVo
import com.ct.framework.jetpack2.vo.UserInfo

@Database(entities = [DetailVo::class, UserInfo::class], version = 1, exportSchema = false)
abstract class GankDatabase : RoomDatabase() {
    abstract fun getGankIoDao(): GanKIoDao

    abstract fun getUserInfoDao(): UserInfoDao
}