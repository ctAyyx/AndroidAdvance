package com.ct.ipc.service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log

/**
 * 采用 Binder实现通讯
 *
 * 测试 同进程下 不同进程通讯
 * */
private const val LOG = "MessengerService"

class MessengerService : Service() {

    private val mHandler by lazy { ServiceMessengerHandler() }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e(LOG, "onBind()")
        return Messenger(mHandler).binder
    }

    override fun unbindService(conn: ServiceConnection) {
        super.unbindService(conn)
        Log.e(LOG, "unbindService()")
    }

    /**
     * 多次 调用 StartService时调用
     * */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(LOG, "onStartCommand--${intent?.getStringExtra("NAME")}")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(LOG, "onDestroy()")
    }


    class ServiceMessengerHandler : Handler() {
        private var clientMessenger: Messenger? = null
        override fun handleMessage(msg: Message) {
            clientMessenger = msg.replyTo

            sendMsgToClient("服务端收到了客户端发送的消息了")
        }

        private fun sendMsgToClient(msg: String) {
            val message = Message.obtain()
            message.what = 0
            // message.obj = msg
            clientMessenger?.send(message)
        }
    }
}