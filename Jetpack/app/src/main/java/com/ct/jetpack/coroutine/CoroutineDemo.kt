package com.ct.jetpack.coroutine

import kotlinx.coroutines.*


/**
 * runBlock 之所以能够阻塞当前线程 是因为调用了  BlockingCoroutine 的 joinBlocking
 * 而 普通的协程作用域 都只是调用了start 方法 这个方法来启动协程
 *
 * 协程是协作的 协程的取消类似线程中的interceptor
 *
 * 理解: 个人认为协程是基于事件机制的 通过 Loop 和 Handle 来处理事件
 *
 *  CoroutineScope      协程环境 提供了协程运行的环境           JVM
 *  CoroutineContext    协程上下文 提供了协程运行的资源         Context
 *  Job                 使得协程可以取消
 *  CoroutineDispatcher 协程调度器 确定协程在那个或那些线程运行
 *
 * */
fun main() {
    println("Main start ....")

    //我们创建 并启动协程
    //newCoroutine()

    //取消协程
    //cancelCoroutine()

    //超时取消
    //withTimeOutCoroutine()

    //并发操作
    //asyncCoroutine()

    //协程调度器
    dispatcherCoroutine()
    println("Main end ....")

}

/**
 * 协程
 * */
private fun newCoroutine() {
    //不会阻塞 主线程的执行
    //但是主线程执行完成 协程还在执行 会抛出异常
//    val job = GlobalScope.launch {
//        println("启动的是全局协程")
//        doWork1()
//    }


    //会阻塞主线程
    //   runBlocking {
//        println("启动的是RunBlock协程")
//        doWork2()
//    }


//    GlobalScope.myLaunch {
//        println("启动的是自定义作用域的协程")
//        doWork2()
//
//    }
}


/**
 * 协程是协作的
 * 可以使用cancel来取消正在运行的协程
 * */
private fun cancelCoroutine() = runBlocking<Unit> {
    //这个可以直接取消
    //   val job = launch {
//        while (true) {
//            println("------->我是子协程")
//            delay(500)
//        }
//    }


    //不可以取消
    //没有检测取消 相当于 线程调用了interceptor方法 但是 我们没有检测isIntercepted
    //解决方法:检测取消状态 需要当前协程 和 父协程在不同的上下文中
    //        调用yield方法 对当前协程 和 父协程的上下文没有要求
    val job = launch(Dispatchers.Default) {
        var i = 0


        //无法取消的协程
//        while (true) {//这里一直在执行计算操作 占用CPU
//            if (i % 100 == 0) {
//                println("------->$isActive 我是子协程$i")
//            }
//            i++
//        }


        try {

            //while (true) {
            //yield()
            while (isActive) {
                if (i % 100 == 0) {
                    println("------->$isActive 我是子协程$i")
                }
                i++
            }
        } finally {
            println("------>finally")
        }
    }

    delay(100)
    job.cancelAndJoin()


}


private fun withTimeOutCoroutine() = runBlocking {

    //会抛出 一个TimeoutCancellationException
//    try {
//       val name =  withTimeout(1300) {
//            while (true) {
//                println("超时测试---->")
//                delay(500)
//            }
//           "Over"
//        }
//    } catch (e: TimeoutCancellationException) {
//        println("捕获到了超时异常")
//    }


    //不会抛出异常 超时返回一个Null 值
    val name = withTimeoutOrNull(1300) {
        while (true) {
            println("超时测试---->")
            delay(500)
        }
        "Over"
    } ?: "超时了"

    println("获取的数据:$name")
}


/**
 * 协程的并发操作
 * */
private fun asyncCoroutine() = runBlocking<Unit> {


//挂起函数需要依次执行
//    doWork2()
//    doWork2()


    //直接使用launch 可以并发执行
//    launch {
//        doWork2()
//    }
//    launch {
//        doWork2()
//    }

    //类似启动launch
    val work1 = async { doWork2() }
    val work2 = async { doWork2() }

    work1.await()
    work2.await()


}

/**
 * 协程上下文 和 调度器
 * */
private fun dispatcherCoroutine() = runBlocking<Unit> {

//    launch(Dispatchers.Main) {
//        println("Main当前运行的线程:${Thread.currentThread().name}")
//    }

    //使用后台共享线程池
    launch(Dispatchers.Default) {
        println("Default当前运行的线程:${Thread.currentThread().name}")
    }

    //使用线程池
    launch(Dispatchers.IO) {
        println("IO当前运行的线程:${Thread.currentThread().name}")
    }

    //那里启动 就在哪个上下文中
    //在挂起后
    //那里恢复 就在哪个上下文中
    launch(Dispatchers.Unconfined) {
        println("Unconfined当前运行的线程:${Thread.currentThread().name}")
        delay(500)
        println("Unconfined当前运行的线程:${Thread.currentThread().name}")
    }

    delay(2000)
}


//这里我们创建两个耗时任务
private fun doWork1(): String {
    println("开始请求一个耗时操作(阻塞)")
    Thread.sleep(10_000)
    println("耗时操作完成(阻塞)")
    return "Over"
}

private suspend fun doWork2(): String {
    println("开始请求一个耗时操作(非阻塞)")
    delay(10_000)
    println("耗时操作完成(非阻塞)")
    return "Over"
}
