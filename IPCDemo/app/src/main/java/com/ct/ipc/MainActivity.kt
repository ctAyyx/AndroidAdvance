package com.ct.ipc

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import com.ct.ipc.service.BinderService
import com.ct.ipc.service.MessengerService
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val bindServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.e(TAG, "binder:onServiceDisconnected()")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.e(TAG, "binder:onServiceConnected()")
                binder = service as BinderService.MyBinder?
                binder?.sendMsg("连接成功")
            }
        }
    }
    private var binder: BinderService.MyBinder? = null


    //=============================
    private val messengerServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                Log.e(TAG, "Messenger:onServiceDisconnected()")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                Log.e(TAG, "Messenger:onServiceConnected()")
                serviceMessenger = Messenger(service)
            }
        }
    }

    private var serviceMessenger: Messenger? = null

    private var clientMessenger: Messenger? = null
    private val messengerHandler by lazy { MessengerHandler() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_binder.setOnClickListener {
            bindService()
        }

        btn_messenger.setOnClickListener {
            bindMessengerService()
        }

        btn_messenger_start.setOnClickListener {
startMessengerService()
        }
        btn_messenger_02.setOnClickListener {
            sendMsgToMessengerService()
        }
    }


    /**
     * 绑定 服务
     *
     * 利用 Binder 进行通讯
     * */
    private fun bindService() {
        val intent = Intent(this, BinderService::class.java)
        bindService(intent, bindServiceConnection, Service.BIND_AUTO_CREATE)
    }


    /**
     * 绑定服务
     * 利用 Messenger 进行通讯
     * */
    private fun bindMessengerService() {
        val intent = Intent(this, MessengerService::class.java)
        bindService(intent, messengerServiceConnection, Service.BIND_AUTO_CREATE)
    }

    /**
     * 启动服务(跨进程)
     * */
    private fun startMessengerService() {
        val intent = Intent(this, MessengerService::class.java)
        intent.putExtra("NAME","Tom")
        startService(intent)
    }

    private fun sendMsgToMessengerService() {
        val message = Message.obtain()
        message.what = 1
        if (clientMessenger == null) {
            clientMessenger = Messenger(messengerHandler)
        }
        message.replyTo = clientMessenger

        serviceMessenger?.send(message)
    }
}