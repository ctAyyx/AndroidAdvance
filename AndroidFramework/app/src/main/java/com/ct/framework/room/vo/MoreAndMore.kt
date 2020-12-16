package com.ct.framework.room.vo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


/**
 * 定义一个 获取每个播放列表里面包含的歌曲数量
 * */
data class PlaylistAndSongs(
    @Embedded
    val playlist: PlayList,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "songId",
        associateBy = Junction(PlaylistAndSong::class)
    )
    val songs: List<Song>
)


/**
 * 定义一个 获取每首歌曲被那些播放列表收藏
 * */
data class SongAndPlaylists(
    @Embedded
    val song: Song,
    @Relation(
        parentColumn = "songId",
        entityColumn = "playlistId",
        associateBy = Junction(PlaylistAndSong::class)
    )
    val playlists: List<PlayList>
)