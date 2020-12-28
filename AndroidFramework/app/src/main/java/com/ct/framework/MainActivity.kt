package com.ct.framework

import android.content.Context
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import androidx.collection.SparseArrayCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.ct.framework.jetpack.di.AppModule
import com.ct.framework.jetpack.dto.Category
import com.ct.framework.kt.KtRepository
import com.ct.framework.kt.KtViewModel
import com.ct.framework.kt.KtVmFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private val vm: KtViewModel by viewModels { KtVmFactory(KtRepository(AppModule.getServiceApi())) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("TAG", "onCreate:$savedInstanceState")
//        vm.getGirlList()
//            .observe(this, Observer {
//                Log.e("TAG", "获取的数据:$it")
//            })

//        getGirlList()
//            .observe(this, Observer {
//                Log.e("TAG", "获取的数据22222:$it")
//            })
        // Log.e("TAG", "开始")
        //a()
        //Log.e("TAG", "完成")


        //关于 协程调度
        //doMain()
        //doMain2()

        //关于 异步流
        //   doMain3()

        toTestFm("FM1")
        toTestFmClick()
        SparseArrayCompat<String>()

    }

    fun onClick(view: View) {
        Log.e("TAG", "点击。。。。")
    }

    fun getGirlList(): LiveData<List<Category>> {
        val repository = KtRepository(AppModule.getServiceApi())
        val result = MutableLiveData<List<Category>>()
        launch {
            val data = repository.getGirlList()
            result.value = data
        }

        return result
    }

    fun a() = launch {
        delay(5000)
        Log.e("TAG", "协程执行")
    }

    override fun onStart() {
        super.onStart()
        Log.e("TAG", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("TAG", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("TAG", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("TAG", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("TAG", "保存数据")
        outState.putString("NAME", "测试的缓存数据")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e("TAG", "恢复数据")
    }


    //关于协程调度问题

    fun doMain() = runBlocking {

        launch {
            Log.e("TAG", "main runBlocking: ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) {
            Log.e("TAG", "Default runBlocking: ${Thread.currentThread().name}")
        }

        launch(Dispatchers.IO) {
            Log.e("TAG", "IO runBlocking: ${Thread.currentThread().name}")
        }



        launch(Dispatchers.Unconfined) {
            Log.e("TAG", "Unconfined runBlocking: ${Thread.currentThread().name}")
        }

    }

    fun doMain2() = runBlocking(Dispatchers.Unconfined) {

        Log.e("TAG", "开始: ${Thread.currentThread().name}")
        toLate()
        Log.e("TAG", "结束: ${Thread.currentThread().name}")

    }

    private suspend fun toLate() {

        withContext(newSingleThreadContext("MyThread")) {
            delay(3000)
            Log.e("TAG", "完成了.....")
        }
    }


    /****************************异步流***********************************/
    private fun doMain3() {

        "".let { }
        "".takeIf {
            true
        }
        "".run { }
        "".apply {

        }
        "".also {

        }
        with("") {

        }

        //会阻塞当前的线程
        sequence().forEach {
            Log.e("TAG", "Sequence异步流:$it -- ${Thread.currentThread().name}")

        }
    }

    private fun sequence(): Sequence<Int> {

        return sequence {
            //test(this)
            Log.e("TAG", "Sequence异步流:${Thread.currentThread().name}")
            for (i in 1..3) {
                //delay(5000)
                yield(i)
            }
        }
    }

    private fun test(sequence: SequenceScope<Int>) {
        with(sequence) {
            for (i in 1..3) {
                //delay(5000)
                //yield(i)
            }
        }
    }


    private fun toTestFm(tag: String) {

        val transaction = supportFragmentManager.beginTransaction()
        val fm1 = MainFragment()
        fm1.TAG = tag
        transaction.replace(R.id.fm_main, fm1)
        transaction.addToBackStack(tag)
        //transaction.add(R.id.fm_main,fm1)
        //transaction.hide(fm1)

        transaction.commit()
    }

    private fun toTestFmClick() {
        btn_main_fm.setOnClickListener {
            toTestFm("FM2")
        }
    }

}

class MainFragment : Fragment() {

    var TAG = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e(TAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e(TAG, "onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG, "onDetach")
    }
}