package com.ct.ipc

import android.os.Handler
import android.os.Message
import android.util.Log

private const val TAG = "MessengerHandler"

class MessengerHandler : Handler() {

    override fun handleMessage(msg: Message) {
        Log.e(TAG, "获取的消息:${msg.obj}")
    }
}