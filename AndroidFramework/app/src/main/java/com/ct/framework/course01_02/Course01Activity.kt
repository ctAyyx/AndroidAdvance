package com.ct.framework.course01_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.ct.framework.R
import kotlin.concurrent.thread

class Course01Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course01)
        Thread.sleep(3000)
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_course01_01 -> {
                doTest01()
            }
        }
    }


    private fun doTest01() {
        thread(start = true) {
            Looper.prepare()
            Toast.makeText(this, "当前线程:${Thread.currentThread().name}", Toast.LENGTH_LONG).show()
            Looper.loop()
        }
    }


    @RequiresApi(api = 23)
    private fun doTest02() {
        val handler = Handler()

        handler.looper.queue.addIdleHandler {

            true // 执行完成之后 在下一次Handler空闲的时候 再次执行
            false //执行完成之后 直接移除这个IdleHandler
        }

    }

}