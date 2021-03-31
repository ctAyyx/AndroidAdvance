package com.ct.framework.kt

import android.os.Parcel
import android.os.Parcelable

class Demo {

val b = B(1)
}

class B (val age:Int): C(), Parcelable {


    constructor(parcel: Parcel) : this(parcel.readInt()){

    }
    fun aa(){

    }
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeInt(age)
    }

    companion object CREATOR : Parcelable.Creator<B> {
        override fun createFromParcel(parcel: Parcel): B {
            return B(parcel)
        }

        override fun newArray(size: Int): Array<B?> {
            return arrayOfNulls(size)
        }
    }

}

open class A(var name: String) {

}