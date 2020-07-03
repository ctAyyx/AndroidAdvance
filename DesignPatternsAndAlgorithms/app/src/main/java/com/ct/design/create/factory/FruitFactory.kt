package com.ct.design.create.factory

import com.ct.design.vo.fruit.Apple
import com.ct.design.vo.fruit.Fruit
import com.ct.design.vo.fruit.Pear

/**
 * 抽象出来的工厂
 * */
interface FruitFactory {

    fun getFruit(): Fruit
}


class AppleFactory : FruitFactory {
    override fun getFruit(): Fruit = Apple()

}

class PearFactory : FruitFactory {
    override fun getFruit(): Fruit = Pear()

}