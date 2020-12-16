package com.ct.framework.binder

import android.app.Service
import android.content.Intent
import android.os.IBinder

class IPCService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return IPCStub()
    }
}