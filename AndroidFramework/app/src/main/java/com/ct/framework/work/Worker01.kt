package com.ct.framework.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class Worker01(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    /**
     *doWork() 方法在 WorkManager 提供的后台线程上同步运行。
     * */
    override fun doWork() = runBlocking<Result> {

        uploadDetail()
        Result.success()
    }


//            : Result {
//        Log.e("TAG", "doWork工作的线程:${Thread.currentThread().name}")
//
//        //我们在这里面做工作任务
//        uploadDetail()
//        //这里指示工作是否正常完成
//        //从 doWork() 返回的 Result 会通知 WorkManager 服务工作是否成功，以及工作失败时是否应重试工作。
//        //    Result.success()：工作成功完成。
//        //    Result.failure()：工作失败。
//        //    Result.retry()：工作失败，应根据其重试政策在其他时间尝试。
//        return Result.success()
//    }


    private suspend fun uploadDetail() {
        delay(10_000)
        Log.e("TAG", "完成工作...")
    }
}