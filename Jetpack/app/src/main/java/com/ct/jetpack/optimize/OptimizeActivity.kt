package com.ct.jetpack.optimize

import android.Manifest
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import androidx.core.util.set
import com.ct.jetpack.R
import java.util.HashMap
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

/**
 * 性能优化
 * */
class OptimizeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_optimize)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 2000
            )
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_opt01 -> doM()
            R.id.btn_opt02 -> doMap()
            R.id.btn_opt03 -> doDraw()
        }
    }


    /**
     * 测试内存抖动
     * */
    private fun doM() {
        //先测试主线程是否会ANR
//主线程是循环会出现ANR

        thread(start = true) {
            while (true) {
                doM1()
            }
        }


    }

    private var paint: Paint? = null
    private fun doM1() {
        paint = Paint()
        // Log.e("TAG","创建----》")
    }


    /**
     * 测试 HashMap SparseArray 效率
     * */
    private fun doMap() {

        //先测试 添加数据
        val map = HashMap<Int, String>()
        val time01 = measureTimeMillis {

            for (i in 0..1000000) {
                map[i] = "Hash$i"
            }
        }

        val t0 = System.currentTimeMillis()
        val data = map[41313]
        val t1 = System.currentTimeMillis()
        val time1 = t1 - t0
        Log.e("TAG", "=====>HashMap添加数据 耗时$time01  --- $time1")
        val sparse = SparseArray<String>()
        val time02 = measureTimeMillis {

            for (i in 0..1000000) {
                sparse[i] = "Sparse$i"
            }
        }

        val t2 = System.currentTimeMillis()
        val data1 = sparse[41313]
        val t3 = System.currentTimeMillis()
        val time2 = t3 - t2
        Log.e("TAG", "=====>SparseArray添加数据 耗时$time02 --- $time2")
    }

    /**
     * 无效绘制
     * */
    private fun doDraw() {
        val cusView = findViewById<CustomView>(R.id.cusView)
        cusView.startDraw()
    }


}