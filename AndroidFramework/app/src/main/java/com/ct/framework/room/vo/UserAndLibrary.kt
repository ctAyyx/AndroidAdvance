package com.ct.framework.room.vo

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndLibrary(
    @Embedded
    val user: RoomUser,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val library: Library
)