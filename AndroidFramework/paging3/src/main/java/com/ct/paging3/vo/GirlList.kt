package com.ct.paging3.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GirlList(
    @PrimaryKey
    val id: String,
    val title: String,
    val imgUrl: String,
    val author: String
) {
    override fun toString(): String {
        return title
    }
}