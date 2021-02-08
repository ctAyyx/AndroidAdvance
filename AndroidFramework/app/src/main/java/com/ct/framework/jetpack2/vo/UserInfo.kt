package com.ct.framework.jetpack2.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo(
    @PrimaryKey
    val id: String,
    val name: String,
    val age: Int
)