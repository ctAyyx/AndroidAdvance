package com.ct.jetpack.coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@OptIn(InternalCoroutinesApi::class)
class MyCoroutineScope(
    parent: CoroutineContext
) : AbstractCoroutine<Unit>(parent, true) {

//    @Suppress("UNCHECKED_CAST")
//    fun joinBlocking(): T {
//        registerTimeLoopThread()
//        try {
//            eventLoop?.incrementUseCount()
//            try {
//                while (true) {
//                    @Suppress("DEPRECATION")
//                    if (Thread.interrupted()) throw InterruptedException().also { cancelCoroutine(it) }
//                    val parkNanos = eventLoop?.processNextEvent() ?: Long.MAX_VALUE
//                    // note: process next even may loose unpark flag, so check if completed before parking
//                    if (isCompleted) break
//                    parkNanos(this, parkNanos)
//                }
//            } finally { // paranoia
//                eventLoop?.decrementUseCount()
//            }
//        } finally { // paranoia
//            unregisterTimeLoopThread()
//        }
//        // now return result
//        val state = this.state.unboxState()
//        (state as? CompletedExceptionally)?.let { throw it.cause }
//        return state as T
//    }

}

fun CoroutineScope.myLaunch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job {
    val newContext = GlobalScope.newCoroutineContext(context)
    //创建 自定义的协程作用域
    val coroutine = MyCoroutineScope(newContext)
    coroutine.start(start, coroutine, block)
    return coroutine
}


