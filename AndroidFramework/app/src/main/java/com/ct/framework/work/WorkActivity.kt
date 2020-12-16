package com.ct.framework.work

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.ct.framework.R
import kotlinx.coroutines.launch

/**
 * 对 WorkManager 的使用
 *
 * 工作使用 Worker 类定义。doWork() 方法在 WorkManager 提供的后台线程上同步运行。
 * */
class WorkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_work_01 -> doWorker01()
        }
    }

    private fun doWorker01() {

//使用 WorkRequest。Worker 定义工作单元
        val request = OneTimeWorkRequestBuilder<Worker01>()

            .build()

        //    将 WorkRequest 提交给系统
        WorkManager.getInstance(this)

            .enqueue(request)

    }


    /**
     * 自定义 WorkRequest
     * 调度一次性工作和重复性工作
     * 设置工作约束条件，例如要求连接到 Wi-Fi 网络或正在充电
     * 确保至少延迟一定时间再执行工作
     * 设置重试和退避策略
     * 将输入数据传递给工作
     * 使用标记将相关工作分组在一起
     * */
    private fun doWorker02() {
        lifecycleScope.launch {
            whenCreated {

            }
        }

    }

}