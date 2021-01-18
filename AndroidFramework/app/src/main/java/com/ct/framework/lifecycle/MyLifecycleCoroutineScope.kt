package com.ct.framework.lifecycle

import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.CoroutineScope

abstract class MyLifecycleCoroutineScope : CoroutineScope {
    internal abstract val lifecycle: Lifecycle
}