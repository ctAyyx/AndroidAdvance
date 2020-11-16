package com.ct.framework.jetpack.room.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.ct.framework.jetpack.vo.GirlList

@Dao
interface GirlDao {

    @Query("SELECT * FROM girls")
    fun getAll(): DataSource.Factory<Int, GirlList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(list: List<GirlList>)

    @Delete()
    fun clear(vararg girl: GirlList)
}