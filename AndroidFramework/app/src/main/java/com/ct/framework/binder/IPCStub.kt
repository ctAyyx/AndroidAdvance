package com.ct.framework.binder

import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.util.Log


class IPCStub : Binder(), IPCInterface {


    init {
        this.attachInterface(this, DESCRIPTOR)
    }

    companion object {
        const val DESCRIPTOR = "com.ct.framework.IPCInterface"
        const val TRANSACTION_addPerson = FIRST_CALL_TRANSACTION + 0
        const val TRANSACTION_getPerson = FIRST_CALL_TRANSACTION + 1


        fun asInterface(binder: IBinder): IPCInterface {
            Log.e("TAG", "传入进来的IBinder:${binder}")
            val iInterface = binder.queryLocalInterface(DESCRIPTOR)
            if (iInterface != null && iInterface is IPCInterface) {
                return iInterface
            }
            return Proxy(binder)
        }
    }

    private val mList = mutableListOf<Person>()

    override fun addPerson(person: Person) {
        mList.add(person)
    }

    override fun getPerson(): List<Person> {
        return mList
    }

    override fun asBinder(): IBinder {
        return this
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        Log.e("TAG", "${Thread.currentThread().name}获取到远程数据:$code")
        when (code) {
            INTERFACE_TRANSACTION -> {
                reply?.writeString(DESCRIPTOR)
            }
            TRANSACTION_addPerson -> {
                data.enforceInterface(DESCRIPTOR)
                if (data.readInt() != 0) {
                    val person = Person(data)
                    Log.e("TAG", "$person 可以读取数据了")
                    mList.add(person)
                }
                reply?.writeNoException()
            }
            TRANSACTION_getPerson -> {
                data.enforceInterface(DESCRIPTOR)
                reply?.writeNoException()
                reply?.writeTypedList(mList)
            }
        }
        return true
    }
}