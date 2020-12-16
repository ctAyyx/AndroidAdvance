package com.ct.framework.binder

import android.os.IInterface

interface IPCInterface : IInterface {

    fun addPerson(person:Person)

    fun getPerson():List<Person>
}