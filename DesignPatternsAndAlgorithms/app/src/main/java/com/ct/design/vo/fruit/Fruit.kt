package com.ct.design.vo.fruit

import com.ct.design.vo.pack.Pack


/**
 * 水果
 * */
abstract class Fruit {

    abstract fun getName(): String
    abstract fun getPrice(): Double

    //加入打包功能
    open fun addPack(pack: Pack) {
        println("使用${pack.size}-${pack.material}的${pack.packName()}")
    }

}