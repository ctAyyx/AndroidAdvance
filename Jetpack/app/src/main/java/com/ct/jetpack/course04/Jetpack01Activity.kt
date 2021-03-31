package com.ct.jetpack.course04

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.ct.jetpack.R
import com.ct.jetpack.databinding.ActivityJetpack01Binding

/**
 * Jetpack 套件
 *
 * Lifecycle 和 LiveData
 *
 * LifecycleOwner   是一个接口 提供了获取Lifecycle的方法
 * Lifecycle        可以感知生命周期的类 并通知其它观察者
 * */
class Jetpack01Activity : AppCompatActivity() {

    private lateinit var binding: ActivityJetpack01Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_jetpack01)
        binding = DataBindingUtil.setContentView<ActivityJetpack01Binding>(
            this,
            R.layout.activity_jetpack01
        )
        toTestLiveDataBus()
    }


    private fun registerLifecycle() {

        val observer01 = MyLifecycleObserver01()
        //源码 是如何通知观察者的???
        lifecycle.addObserver(observer01)

    }


    private fun useLiveData() {
        val data = MutableLiveData<String>()
        //源码 如何实现数据更新的
        data.observe(this) {

        }

        data.value = "AA"
    }


    /**
     * 源码的思维导图 都在 百度脑图
     * */
    private fun useDataBinding() {
        //对 DataBinding 的使用
        //源码 如何实现数据自动刷新到界面的??

    }


    private fun useViewModel() {
        //源码 查看ViewModel 的创建 和生命周期
        val vm = ViewModelProvider(this)
            .get(MyViewModel::class.java)

    }


    override fun onRetainCustomNonConfigurationInstance(): Any? {
        Log.e("TAG", "onRetainCustomNonConfigurationInstance")
        return super.onRetainCustomNonConfigurationInstance()
    }

    override fun getLastNonConfigurationInstance(): Any? {
        Log.e("TAG", "getLastNonConfigurationInstance")
        return super.getLastNonConfigurationInstance()
    }


    private fun toTestLiveDataBus() {
        //LiveDataBus.get().with("Sticker", String::class.java).value = "发送新的事件"

        LiveDataBus.get().with("Sticker", String::class.java)
            .observe(this) {
                Log.e("TAG", "$this-->LiveDataBus粘性事件:$it")
            }

    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_lifecycle -> registerLifecycle()
            R.id.btn_liveDataBus -> toTestLiveDataBus()
        }
    }


}