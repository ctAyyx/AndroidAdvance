package com.ct.framework.binder

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import com.ct.framework.R

class IPCClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

    }


    private var binder: IPCInterface? = null
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
            binder = IPCStub.asInterface(service)

            Log.e("TAG", "连接成功---->$binder")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }


    }

    fun onClick(view: View) {
        when (view.id) {

            R.id.btn_ipc_01 -> bindService(
                Intent(this, IPCService::class.java),
                connection,
                Service.BIND_AUTO_CREATE
            )

            R.id.btn_ipc_02 -> {
                val person = Person("小明", 10)
                binder?.addPerson(person = person)
            }
            R.id.btn_ipc_03 -> {
                val persons = binder?.getPerson()
                Log.e("TAG", "获取到的数据:${persons}")
            }
        }
    }
}