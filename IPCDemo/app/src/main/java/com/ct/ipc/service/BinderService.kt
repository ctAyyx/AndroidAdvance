package com.ct.ipc.service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log

/**
 * 采用 Binder实现通讯
 *
 * 测试 同进程下 不同进程通讯
 *
 *
 * 结论: 使用Binder通讯 需要在同一应用的同一进程下才有效
 * */
private const val LOG = "BinderService"

class BinderService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        Log.e(LOG, "onBind()")
        return MyBinder()
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        Log.e(LOG, "unbindService()")
    }

    /**
     * 多次 调用 StartService时调用
     * */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(LOG, "onDestroy()")
    }


    class MyBinder : Binder() {

        fun sendMsg(msg: String) {
            Log.e(LOG, "服务端获取到的数据:$msg")
        }
    }

}