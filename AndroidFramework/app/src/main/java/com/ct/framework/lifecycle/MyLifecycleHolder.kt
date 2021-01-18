package com.ct.framework.lifecycle

import androidx.lifecycle.*
import kotlinx.coroutines.launch

/**
 * 自定义 Lifecycle
 *
 * 1.实现生命周期
 * 2.实现协程作用域的生命周期感知
 * */
open class MyLifecycleHolder : LifecycleOwner {

    private lateinit var lifecycleRegister: LifecycleRegistry


    init {
        lifecycleRegister = LifecycleRegistry(this)
        lifecycleRegister.currentState = Lifecycle.State.INITIALIZED
    }

    fun onCreate() {
        lifecycleRegister.currentState = Lifecycle.State.CREATED
    }

    fun onStart() {
        lifecycleRegister.currentState = Lifecycle.State.STARTED
    }

    fun onResume() {
        lifecycleRegister.currentState = Lifecycle.State.RESUMED
    }

    fun onPause() {
        lifecycleRegister.currentState = Lifecycle.State.STARTED
    }

    fun onStop() {
        lifecycleRegister.currentState = Lifecycle.State.CREATED
    }

    fun onDestroy() {
        lifecycleRegister.currentState = Lifecycle.State.DESTROYED
    }


    override fun getLifecycle(): Lifecycle {
        return lifecycleRegister
    }

}