package com.ct.framework.room.vo

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndPlayListAndSong(
    @Embedded
    val user: RoomUser,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId",
        entity = PlayList::class
    )
    val playlistAndSongs: List<PlaylistAndSongs>
)