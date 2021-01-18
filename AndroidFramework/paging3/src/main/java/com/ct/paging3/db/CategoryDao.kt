package com.ct.paging3.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ct.paging3.vo.GirlList

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<GirlList>)

    @Query("SELECT * FROM girllist")
    fun pagingSource(): PagingSource<Int, GirlList>

    @Query("DELETE FROM girllist")
    suspend fun clearAll()

    @Query("SELECT * FROM girllist")
    suspend fun getAllCategory(): List<GirlList>

    @Query("SELECT COUNT(*) FROM girllist")
    suspend fun  getCategorySize(): Int

    @Query("SELECT * FROM girllist WHERE id=:id AND title=:title ")
    suspend fun getCategory(id: String, title: String): List<GirlList>

}