package com.ct.jetpack.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ct.jetpack.R
import kotlinx.coroutines.*

/**
 *
 * 看完了CoroutineDemo
 * 了解了在协程中使用普通阻塞方法 和 挂起方法的差别
 *
 * 看 lifecycleScope 是如何控制生命周期的
 *
 * */
class CoroutineActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
    }
    //RunBlocking 直接回阻塞主线程的执行
//    override fun onCreate(savedInstanceState: Bundle?) = runBlocking<Unit> {
//        super.onCreate(savedInstanceState)
//        Log.e("TAG", "RunBlocking start")
//        launch(Dispatchers.IO) {
//            doWork2()
//        }
//
//        setContentView(R.layout.activity_coroutine)
//        Log.e("TAG", "RunBlocking end")
//    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_coroutine_01 -> useCoroutines()
        }
    }


    /**
     * 我们先校验一下 协程是否会阻塞当前线程
     * */
    private fun useCoroutines() {
        //直接运行的情况下 直接ANR
//        val name = doWork()
//        Toast.makeText(this, name, Toast.LENGTH_LONG).show()

        Log.e("TAG", "协程运行前的代码....")

        //这里使用协程
        //依然出现了ANR
        //阻塞了当前线程的执行
//        lifecycleScope.launch {
//            val name = doWork()
//            Toast.makeText(this@MainActivity, name, Toast.LENGTH_LONG).show()
//        }

        //这里使用协程 我们切换到工作线程
        //协程中的代码会挂起 但是不会影响线程代码的正常执行
        lifecycleScope.launch {
            //这里如果我们不主动切换 协程上下文
            //则从父协程继承 这里是 主线程的作用域
            //所以 如果做得是耗时操作 则会阻塞主线程
            val name = withContext(Dispatchers.Default) { doWork() }

            Toast.makeText(this@CoroutineActivity, name, Toast.LENGTH_LONG).show()
        }


        //这里校验liveData的扩展
        //依旧会出现ANR
//        liveData {
//            val name = doWork()
//            emit(name)
//        }.observe(this) { name ->
//            Toast.makeText(this@MainActivity, name, Toast.LENGTH_LONG).show()
//        }


        //现在开始使用 suspend 挂起函数测试
//        lifecycleScope.launch {
//            val name = doWork2()
//            Toast.makeText(this@MainActivity, name, Toast.LENGTH_LONG).show()
//        }


//        liveData {
//            val name = doWork2()
//            emit(name)
//        }.observe(this) { name ->
//            Toast.makeText(this@MainActivity, name, Toast.LENGTH_LONG).show()
//        }


        Log.e("TAG", "协程运行后的代码....")


        //由此 总结出
        //在协程中 调用普通的方法 如果该方法有耗时操作 需要切换到IO上
        //挂起函数则不会阻塞线程的执行
    }

    /**
     * 这里模拟耗时操作
     * */
    private fun doWork(): String {
        Log.e("TAG", "开始进行工作")
        Thread.sleep(10_000)
        Log.e("TAG", "完成工作")
        return "Over"
    }

    private suspend fun doWork2(): String {
        Log.e("TAG", "开始进行工作")
        delay(10_000)
        Log.e("TAG", "完成工作")
        return "Over"
    }


}