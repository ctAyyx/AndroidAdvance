package com.ct.framework.binder

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator


data class Person(
    val name: String,
    val age: Int
) : Parcelable {
    constructor(data: Parcel) : this(data.readString() ?: "", data.readInt())

    companion object {
        val CREATOR: Creator<Person> = object : Creator<Person> {
            override fun createFromParcel(source: Parcel): Person {
                return Person(source)
            }

            override fun newArray(size: Int): Array<Person> {
                return newArray(size)
            }
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeInt(age)
    }
}