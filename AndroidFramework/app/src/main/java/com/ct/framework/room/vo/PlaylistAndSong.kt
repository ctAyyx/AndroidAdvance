package com.ct.framework.room.vo

import androidx.room.Entity

/**
 * 定义一个 多对多关系中
 * 两个实体之间的关联实体（即交叉引用表）
 *交叉引用表中必须包含表中表示的多对多关系中每个实体的主键列
 * */
@Entity(primaryKeys = ["playlistId", "songId"])
data class PlaylistAndSong(
    val playlistId: Int,
    val songId: Int
)