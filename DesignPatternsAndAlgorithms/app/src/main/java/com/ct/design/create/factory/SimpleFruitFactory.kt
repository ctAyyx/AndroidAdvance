package com.ct.design.create.factory

import com.ct.design.vo.fruit.Apple
import com.ct.design.vo.fruit.Fruit
import com.ct.design.vo.fruit.Pear

/**
 * 简单工厂模式
 * */
const val APPLE = 1
const val PEAR = 2

class SimpleFruitFactory {

    fun getFruit(type: Int): Fruit {

        if (type == APPLE)
            return Apple()
        else if (type == PEAR)
            return Pear()

        throw IllegalStateException("没有对应的水果")
    }
}