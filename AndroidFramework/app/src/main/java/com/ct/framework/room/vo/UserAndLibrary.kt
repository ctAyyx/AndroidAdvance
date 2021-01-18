package com.ct.framework.room.vo

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndLibrary(

    //需要使用  @Embedded 注解
    //Room数据库在编译的时候才能将 userId 和 userOwnerId 关联起来
    @Embedded
    val user: RoomUser,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userOwnerId"
    )
    val library: Library
)