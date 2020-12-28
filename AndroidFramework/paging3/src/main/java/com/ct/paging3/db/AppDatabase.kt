package com.ct.paging3.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ct.paging3.vo.GirlList


@Database(entities = [GirlList::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun girlDao(): CategoryDao
}