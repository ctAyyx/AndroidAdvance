package com.ct.design.structure.bridge

interface Size {

    fun getSize(): String
}

class SmallSize : Size {
    override fun getSize(): String = "小号的"

}

class MediumSize : Size {
    override fun getSize(): String = "中号的"

}

class BigSize : Size {
    override fun getSize(): String = "大号的"

}