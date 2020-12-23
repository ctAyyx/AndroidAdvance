package com.ct.framework.room.vo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RoomUser")
data class RoomUser(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    @ColumnInfo(name = "name")//字段别名
    val name: String,
    val age: Int,
    val sex: Boolean = false,
    @Embedded //创建嵌套对象
    val address: Address?,
    val photos: List<String>,
    val photos2: List<Int>
)