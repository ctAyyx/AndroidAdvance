package com.ct.framework.room.vo

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndPlayList(
    @Embedded
    val user: RoomUser,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userCreatorId"
    )
    val playList: List<PlayList>
)