package com.ct.framework.binder

import android.os.IBinder
import android.os.Parcel

class Proxy(private val mRemote: IBinder) : IPCInterface {


    override fun addPerson(person: Person) {
        //创建 远程包裹
        val data = Parcel.obtain()
        val reply = Parcel.obtain()

        try {

            data.writeInterfaceToken(getInterfaceDescriptor())
            data.writeInt(1)
            person.writeToParcel(data, 0)
            mRemote.transact(IPCStub.TRANSACTION_addPerson, data, reply, 0)
            reply.readException()
        } finally {
            reply.recycle()
            data.recycle()
        }

    }

    override fun getPerson(): List<Person> {
        val data = Parcel.obtain()
        val reply = Parcel.obtain()

        try {

            data.writeInterfaceToken(getInterfaceDescriptor())
            mRemote.transact(IPCStub.TRANSACTION_getPerson, data, reply, 0)
            reply.readException()
            return reply.createTypedArrayList(Person.CREATOR)?.toList() ?: emptyList()

        } finally {
            reply.recycle()
            data.recycle()

        }
        return emptyList()
    }

    override fun asBinder(): IBinder {
        return mRemote
    }

    fun getInterfaceDescriptor() = IPCStub.DESCRIPTOR

}