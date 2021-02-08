package com.ct.jetpack.course05

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresPermission
import com.ct.jetpack.R

/**
 * WorkManager 的基本使用
 * 源码解析
 *
 *
 * 在使用WorkManager之前 了解Google 后台任务
 * 1. 对任务的划分 即时任务 精确任务 延迟任务
 * 2. 保持设备常亮 和 CPU唤醒操作
 * 3. 重复闹钟
 * */
class Course05Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course05)
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_course05_keepOn -> {
                keepScreenOn()
            }
            R.id.btn_course05_CpuOn -> {
            }
            R.id.btn_course05_alarm -> {
            }
        }

    }

    /**
     * 保持设备常亮
     *
     *
     * */
    private fun keepScreenOn() {
        //保持设备常亮
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //如果需要屏幕再次可以关闭
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //我们也可以在xml布局中 加入android:keepScreenOn="true"
    }

    /**
     * 保持 CPU唤醒
     *
     * 1. 需要唤醒锁的权限
     * */
    @RequiresPermission(Manifest.permission.WAKE_LOCK)
    private fun keepCpuOn() {

        //获取 PowerManager
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager

        //创建一个新的唤醒锁
        //参数1: 唤醒锁的等级
        //参数2: TAG 形如 XXX::XXX
        val wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakeLock")

        //保持CPU唤醒
        wakeLock.acquire()

        //释放CPU
        wakeLock.release()

    }

    private fun repeatAlarm() {

    }

    /********************* Work Manager ***********************/


}