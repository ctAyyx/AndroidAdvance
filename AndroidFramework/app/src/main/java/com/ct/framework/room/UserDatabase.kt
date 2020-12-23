package com.ct.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ct.framework.room.converter.PhotoConverter
import com.ct.framework.room.dao.UserDao
import com.ct.framework.room.vo.*

@Database(
    entities = [RoomUser::class, Library::class, PlayList::class, Song::class, PlaylistAndSong::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(PhotoConverter::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
}