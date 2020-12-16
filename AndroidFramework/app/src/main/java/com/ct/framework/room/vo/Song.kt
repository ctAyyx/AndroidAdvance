package com.ct.framework.room.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Song(
    val songName: String,
    val artist: String,
    @PrimaryKey(autoGenerate = true)
    val songId: Int = 0

)