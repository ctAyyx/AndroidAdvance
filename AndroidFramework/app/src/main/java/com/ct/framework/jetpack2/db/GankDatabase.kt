package com.ct.framework.jetpack2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ct.framework.jetpack2.db.dao.GanKIoDao
import com.ct.framework.jetpack2.vo.DetailVo

@Database(entities = [DetailVo::class], version = 1,exportSchema = false)
abstract class GankDatabase : RoomDatabase() {
    abstract fun getGankIoDao(): GanKIoDao
}