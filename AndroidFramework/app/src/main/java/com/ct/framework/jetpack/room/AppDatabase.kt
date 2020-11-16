package com.ct.framework.jetpack.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ct.framework.jetpack.room.dao.GirlDao
import com.ct.framework.jetpack.vo.GirlList

@Database(entities = [GirlList::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun girlDao(): GirlDao
}