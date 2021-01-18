package com.ct.framework.lifecycle

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


val Lifecycle.myLifeScope: MyLifecycleCoroutineScope
    get() {
        val scrope =
            MyLifecycleCoroutineScopeImp(this, SupervisorJob() + Dispatchers.Main.immediate)
        scrope.register()
        return scrope
    }