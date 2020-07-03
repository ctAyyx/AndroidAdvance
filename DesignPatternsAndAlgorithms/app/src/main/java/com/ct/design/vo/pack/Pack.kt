package com.ct.design.vo.pack

/**
 * 水果包装盒接口
 * */
interface Pack {

    val size: String
    val material: String

    fun packName(): String
}

