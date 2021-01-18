package com.ct.framework.binder

import android.app.Service
import android.content.Intent
import android.os.IBinder

class IPCService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return MyIPCStub()
    }


    class MyIPCStub : IPCStub() {

        //是本地方法调用

        private val mList = mutableListOf<Person>()
        override fun addPerson(person: Person) {
            mList.add(person)
        }

        override fun getPerson(): List<Person> {
            return mList;
        }

    }
}