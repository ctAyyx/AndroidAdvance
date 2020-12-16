package com.ct.framework.room.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Library(
    @PrimaryKey
    val libraryId: Int,
    val userOwnerId: Int,
    val libName: String,
    val count: Int
)