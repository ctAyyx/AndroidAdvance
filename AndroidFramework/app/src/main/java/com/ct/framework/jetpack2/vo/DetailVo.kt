package com.ct.framework.jetpack2.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detailVos")
data class DetailVo(
    @PrimaryKey
    val id: String,
    val author: String,
    val type: String,
    val image: String,
    val time: String
) : MonitorVo()