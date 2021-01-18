package com.ct.framework.lifecycle

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MyLifecycleCoroutineScopeImp(
    override val lifecycle: Lifecycle,
    override val coroutineContext: CoroutineContext
) : MyLifecycleCoroutineScope(), LifecycleObserver {

    fun register() {
        launch(Dispatchers.Main.immediate) {
            if (lifecycle.currentState >= Lifecycle.State.CREATED) {
                lifecycle.addObserver(this@MyLifecycleCoroutineScopeImp)
            } else {
                coroutineContext.cancel()
            }
        }
    }

//    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//        Log.e("TAG", "当前协程生命状态:${lifecycle.currentState}")
//        if (lifecycle.currentState <= Lifecycle.State.STARTED) {
//
//            lifecycle.removeObserver(this)
//            coroutineContext.cancel()
//        }
//
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDestroy() {
        lifecycle.removeObserver(this)
        coroutineContext.cancel()
    }

}