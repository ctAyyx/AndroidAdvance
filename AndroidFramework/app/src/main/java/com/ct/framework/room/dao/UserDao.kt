package com.ct.framework.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.ct.framework.room.vo.*

@Dao
interface UserDao {

    @Query("SELECT * FROM roomuser ")
    fun getAllUser(): LiveData<List<RoomUser>>

    @Insert
    fun insertUser(vararg user: RoomUser)

    @Insert
    fun insertLibrary(vararg library: Library)

    @Insert
    fun insertPlayList(vararg playList: PlayList)

    @Insert
    fun insertSong(vararg song: Song)

    @Insert
    fun insertPlaylistAndSong(vararg playlistAndSong: PlaylistAndSong)


    //这里因为需要查询两次 所以需要添加事务
    @Transaction
    @Query("SELECT * FROM roomuser")
    fun getUserAndLibrary(): LiveData<List<UserAndLibrary>>

    @Transaction
    @Query("SELECT * FROM roomuser")
    fun getUserAndPlayList(): LiveData<List<UserAndPlayList>>

    @Transaction
    @Query("SELECT * FROM playlist")
    fun getPlaylistAndSongs(): LiveData<List<PlaylistAndSongs>>

    @Transaction
    @Query("SELECT * FROM song")
    fun getSongAndPlaylists(): LiveData<List<SongAndPlaylists>>

    @Transaction
    @Query("SELECT * FROM roomuser")
    fun getUserAndPlaylistAndSongs(): LiveData<List<UserAndPlayListAndSong>>


    //============== 对SQLite语法练习 ================


}