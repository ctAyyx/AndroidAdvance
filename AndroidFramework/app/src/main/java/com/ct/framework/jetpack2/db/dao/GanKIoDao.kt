package com.ct.framework.jetpack2.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ct.framework.jetpack2.vo.DetailVo

@Dao
interface GanKIoDao {


    @Query("SELECT * FROM detailVos WHERE id=:id")
    fun getDetailById(id: String): LiveData<DetailVo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetail(model: DetailVo)

    @Query("SELECT * FROM detailVos WHERE id=:id")
    suspend fun getDetailByIdWithCoroutines(id: String): DetailVo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailWithCoroutines(model: DetailVo)

}