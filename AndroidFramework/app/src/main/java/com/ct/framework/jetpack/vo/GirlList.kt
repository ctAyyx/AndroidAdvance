package com.ct.framework.jetpack.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "girls")
data class GirlList(
    @PrimaryKey
    val id: String,
    val title: String,
    val imgUrl: String,
    val author: String
)