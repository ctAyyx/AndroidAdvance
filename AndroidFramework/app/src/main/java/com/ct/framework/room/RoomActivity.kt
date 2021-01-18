package com.ct.framework.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.room.Room
import com.bumptech.glide.util.LruCache
import com.ct.framework.R
import com.ct.framework.room.vo.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {

    private val db by lazy {
        Room.databaseBuilder(this, UserDatabase::class.java, "room_demo")
            .fallbackToDestructiveMigration()
            .build()
    }

    private val userDao by lazy { db.getUserDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_room_01 -> lifecycleScope.launch(Dispatchers.Default) {
                insert01()
                insert02()
                insert03()
                insert04()
            }
            R.id.btn_room_02 -> useRoom01()
            R.id.btn_room_03 -> useRoom02()
            R.id.btn_room_04 -> useRoom03()
            R.id.btn_room_05 -> useRoom04()
            R.id.btn_room_06 -> useRoom05()
        }
    }


    /**
     * 进行 单个实体的操作
     *
     * 嵌套对象
     * */
    private fun useRoom01() {
        userDao.getAllUser()
            .observe(this) {
                Log.e("TAG", "获取到的数据:${it}")
            }

    }

    private suspend fun insert01() {
        val address1 = Address("中国", "成都")
        val address2 = Address("中国", "北京")
        val users = arrayOf(
            RoomUser(
                name = "张三2",
                age = 10,
                address = address1,
                photos = mutableListOf("13540366155", "13540366156"),
                photos2 = mutableListOf(123, 456)

            ),
            RoomUser(
                name = "李四2",
                age = 15,
                address = address2,
                photos = mutableListOf("18080947066", "18080947067"),
                photos2 = mutableListOf(147, 258)
            )
        )
        userDao.insertUser(*users)

    }


    /**
     *定义一对一关系
     *
     * 例如，假设有一个音乐在线播放应用，用户在该应用中具有一个属于自己的歌曲库。每个用户只有一个库，而且每个库恰好对应于一个用户。因此，User 实体和 Library 实体之间就应存在一种一对一的关系。
     * 首先，为您的两个实体分别创建一个类。其中一个实体(Library)必须包含一个变量，且该变量是对另一个实体的主键的引用。
     *
     * 如需查询用户列表和对应的库，您必须先在两个实体之间建立一对一关系。
     * 为此，请创建一个新的数据类(UserAndLibrary)，其中每个实例都包含父实体的一个实例和与之对应的子实体实例。
     * 将 @Relation 注释添加到子实体的实例，同时将 parentColumn 设置为父实体主键列的名称，并将 entityColumn 设置为引用父实体主键的子实体列的名称
     *
     * */
    private fun useRoom02() {
        userDao.getUserAndLibrary()
            .observe(this) {
                Log.e("TAG", "获取的数据:${it}")
            }
    }

    private suspend fun insert02() {
        val lib1 =
            Library(libraryId = 1, userOwnerId = 1, libName = "网抑云音乐库", count = 12)
        val lib2 =
            Library(libraryId = 2, userOwnerId = 2, libName = "酷狗音乐库", count = 22)
        userDao.insertLibrary(lib1, lib2)
    }


    /**
     * 定义一对多关系
     *
     * 每个用户可以创建任意数量的播放列表，但每个播放列表只能由一个用户创建。因此，User 实体和 Playlist 实体之间应存在一种一对多关系
     * 子实体必须包含一个变量，且该变量是对父实体的主键的引用。
     *
     * 为了查询用户列表和对应的播放列表，您必须先在两个实体之间建立一对多关系。
     * 为此，请创建一个新的数据类(UserAndPlayList)，其中每个实例都包含父实体的一个实例和与之对应的所有子实体实例的列表。
     * 将 @Relation 注释添加到子实体的实例，同时将 parentColumn 设置为父实体主键列的名称，并将 entityColumn 设置为引用父实体主键的子实体列的名称。
     *
     * */
    private fun useRoom03() {

        userDao.getUserAndPlayList().observe(this) {
            Log.e("TAG", "获取到的数据:${it}")
        }
    }


    private suspend fun insert03() {
        val play = PlayList(playlistId = 1, userCreatorId = 1, playListName = "喜欢的")
        val play2 = PlayList(playlistId = 2, userCreatorId = 1, playListName = "夜店DJ")
        val play3 = PlayList(playlistId = 3, userCreatorId = 1, playListName = "韩国女团")

        val play4 = PlayList(playlistId = 4, userCreatorId = 2, playListName = "90后")
        val play5 = PlayList(playlistId = 5, userCreatorId = 2, playListName = "天王时代")
        val play6 = PlayList(playlistId = 6, userCreatorId = 2, playListName = "轻音乐")
        userDao.insertPlayList(play, play2, play3, play4, play5, play6)
    }


    /**
     * 多对多关系
     * 每个播放列表都可以包含多首歌曲，每首歌曲都可以包含在多个不同的播放列表中。因此，Playlist 实体和 Song 实体之间应存在多对多的关系。
     *
     * 首先，为您的两个实体分别创建一个类(PlayList Song)。
     * 多对多关系与其他关系类型均不同的一点在于，子实体中通常不存在对父实体的引用。
     * 因此，需要创建第三个类来表示两个实体之间的关联实体（即交叉引用表）（PlaylistAndSong）。交叉引用表中必须包含表中表示的多对多关系中每个实体的主键列。
     * 在本例中，交叉引用表中的每一行都对应于 Playlist 实例和 Song 实例的配对，其中引用的歌曲包含在引用的播放列表中。
     *
     * 下一步取决于您想如何查询这些相关实体。
     * 如果您想查询播放列表和每个播放列表所含歌曲的列表，则应创建一个新的数据类（PlaylistAndSongs），其中包含单个 Playlist 对象，以及该播放列表所包含的所有 Song 对象的列表。
     * 如果您想查询歌曲和每首歌曲所在播放列表的列表，则应创建一个新的数据类(SongAndPlaylists)，其中包含单个 Song 对象，以及包含该歌曲的所有 Playlist 对象的列表
     * */
    private fun useRoom04() {

        userDao.getPlaylistAndSongs().observe(this) {
            Log.e("TAG", "获取的播放列表数据:${Gson().toJson(it)}")
        }

        userDao.getSongAndPlaylists().observe(this) {
            Log.e("TAG", "获取的歌曲所属列表数据:${Gson().toJson(it)}")
        }

    }

    private suspend fun insert04() {
        val songs = arrayOf(
            Song(songName = "世界那么大还是遇见你", artist = "程响"),
            Song(songName = "On My Way", artist = "程响"),
            Song(songName = "我想", artist = "麦小兜"),
            Song(songName = "笑纳", artist = "花童"),
            Song(songName = "前度", artist = "朱雅"),
            Song(songName = "空空如也", artist = "胡66"),
            Song(songName = "NoNoNo", artist = "Apink"),
            Song(songName = "坏女孩", artist = "徐良"),
            Song(songName = "处处吻", artist = "杨千嬅")
        )
        userDao.insertSong(*songs)
        val playlistAndSongs = arrayOf(
            PlaylistAndSong(1, 1),
            PlaylistAndSong(1, 2),
            PlaylistAndSong(1, 3),
            PlaylistAndSong(1, 4),
            PlaylistAndSong(2, 3),
            PlaylistAndSong(2, 4),
            PlaylistAndSong(2, 5),
            PlaylistAndSong(2, 6),
            PlaylistAndSong(3, 5),
            PlaylistAndSong(3, 6),
            PlaylistAndSong(3, 7),
            PlaylistAndSong(3, 8),
            PlaylistAndSong(4, 1),
            PlaylistAndSong(4, 2),
            PlaylistAndSong(4, 3),
            PlaylistAndSong(4, 4),
            PlaylistAndSong(5, 2),
            PlaylistAndSong(5, 3),
            PlaylistAndSong(5, 4),
            PlaylistAndSong(5, 5),
            PlaylistAndSong(6, 4),
            PlaylistAndSong(6, 5),
            PlaylistAndSong(6, 8),
            PlaylistAndSong(6, 9)
        )
        userDao.insertPlaylistAndSong(*playlistAndSongs)
    }


    /**
     *  定义嵌套关系
     *  一个用户 有多个歌单 一个歌单有多首歌曲
     *
     *  数据库
     *  需要定义的类
     *  RoomUser
     *  PlayList
     *  Song
     *
     *  关联实体
     *  PlaylistAndSong
     *
     *
     *  操作实体类
     *  PlaylistAndSongs
     *  UserAndPlayListAndSong
     *
     *
     * */
    private fun useRoom05() {

        userDao.getUserAndPlaylistAndSongs().observe(this) {
            Log.e("TAG", "获取的数据:${Gson().toJson(it)}")
        }
    }


}