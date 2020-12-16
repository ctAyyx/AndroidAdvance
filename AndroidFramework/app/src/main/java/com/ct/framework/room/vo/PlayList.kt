package com.ct.framework.room.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayList(
    @PrimaryKey
    val playlistId: Int,
    val userCreatorId: Int,
    val playListName: String
)