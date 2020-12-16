package com.ct.storage

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * 关于 Android10 文件存储分区的使用
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPermission()

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_storage_01 -> {
                doAppStorage()
            }
            R.id.btn_storage_02 -> {
                doAppStorageExt()
            }

            R.id.btn_storage_03 -> {
                doExtStorage()
            }
            R.id.btn_storage_04 -> {
                // loadExtThumbnailBitmap()
                playExtVideo()
            }
        }
    }


    /**
     * App内部存储目录（应用专属,不需要权限）
     * 内部存储空间目录：这些目录既包括用于存储持久性文件的专属位置，也包括用于存储缓存数据的其他位置。
     * 系统会阻止其他应用访问这些位置，并且在 Android 10（API 级别 29）及更高版本中，系统会对这些位置进行加密。
     * 这些特征使得这些位置非常适合存储只有应用本身才能访问的敏感数据。
     *
     * */
    private fun doAppStorage() {
        thread(start = true) {
            //这里我们获取的是应用内部持久化目录
            val file = File(filesDir, "a.txt")
            writeData(file, "内部存储空间--应用专属--持久化数据")
            Thread.sleep(1000L)
            var data = readData(file)
            Log.e("TAG", "应用专属--持久化数据:$data")

            //这里我们获取的是应用内部缓存目录
            val cacheFile = File(cacheDir, "cache.txt")
            writeData(cacheFile, "内部存储空间--应用专属--缓存数据")
            Thread.sleep(1000L)
            data = readData(cacheFile)
            Log.e("TAG", "应用专属--缓存数据:$data")
            Thread.sleep(1000L)
            //删除缓存文件
            //实践中 没有成功移除
            Log.e("TAG", "移除缓存文件:${deleteFile("cache.txt")}")
            //能够成功移除
            Log.e("TAG", "移除缓存文件:${cacheFile.delete()}")

        }
    }


    /**
     * 如果内部存储空间不足以存储应用专属文件，请考虑改为使用外部存储空间。
     * 系统会在外部存储空间中提供目录，应用可以在该存储空间中整理仅在应用内对用户有价值的文件。
     * 一个目录专为应用的持久性文件而设计，而另一个目录包含应用的缓存文件。

    在 Android 4.4（API 级别 19）或更高版本中，
    应用无需请求任何与存储空间相关的权限即可访问外部存储空间中的应用专属目录。
    卸载应用后，系统会移除这些目录中存储的文件。
     * */
    private fun doAppStorageExt() {

        thread(start = true) {
            val file = File(getExternalFilesDir(null), "ext.txt")
            writeData(file, "外部存储空间--应用专属--持久化数据")
            Thread.sleep(1000L)
            var data = readData(file)
            Log.e("TAG", "应用专属--持久化数据:$data")

            //这里我们获取的是应用内部缓存目录
            val cacheFile = File(externalCacheDir, "cache.txt")
            writeData(cacheFile, "外部存储空间--应用专属--缓存数据")
            Thread.sleep(1000L)
            data = readData(cacheFile)
            Log.e("TAG", "应用专属--缓存数据:$data")
            Thread.sleep(1000L)
        }
    }


    /**
     * 获取 共享存储空间的文件
     * 需要 存储权限
     * */
    private val videos = mutableListOf<Video>()
    private fun doExtStorage() {

        thread(start = true) {


            val projection = arrayOf(
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.SIZE
            )

            val selection = "${MediaStore.Video.Media.DURATION} >= ?"
            val selectionArgs = arrayOf(TimeUnit.MILLISECONDS.convert(5, TimeUnit.SECONDS).toString())
            val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"
            val query = contentResolver
                    .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder)
            query?.use { cursor ->

                val idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idColumn)
                    val name = cursor.getString(nameColumn)
                    val duration = cursor.getInt(durationColumn)
                    val size = cursor.getInt(sizeColumn)
                    val contentUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                    videos.add(Video(id, name, duration, size, contentUri))

                }


            }
            query?.close()

            //打印数据
            Log.e("TAG", "获取到的数据:${videos}")

        }
    }


    /**
     * 加载媒体 缩略图
     * */
    private fun loadExtThumbnailBitmap() {
        val image = findViewById<ImageView>(R.id.img_storage)
        thread(start = true) {
            videos.forEach {
                val thumbnail = contentResolver.loadThumbnail(it.uri, Size(640, 480), null)
                runOnUiThread {
                    image.setImageBitmap(thumbnail)
                }
                Thread.sleep(2000L)
            }
        }


    }

    /**
     * 播放视频
     * */
    private fun playExtVideo() {
        val videoView = findViewById<VideoView>(R.id.video_storage)

        var index = 0
        fun play() {
            val parent = videoView.parent as ViewGroup
            parent.removeView(videoView)
            parent.addView(videoView)
            videoView.setVideoURI(videos[index].uri)
            videoView.start()
        }

        videoView.setOnCompletionListener {
            Log.e("TAG", "完成播放")
            index++
            play()
        }

        play()

    }


    private fun writeData(file: File, content: String) {
        val ous = FileOutputStream(file)
        ous.write(content.toByteArray())
        ous.flush()
        ous.close()
    }

    private fun readData(file: File): String {
        val ins = FileInputStream(file)
        val arr = ByteArray(ins.available())
        ins.read(arr)
        return String(arr)
    }


    private fun initPermission() {

        if (ContextCompat
                        .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat
                        .checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
        }

    }


    data class Video(val id: Long, val name: String, val duration: Int, val size: Int, val uri: Uri)

}