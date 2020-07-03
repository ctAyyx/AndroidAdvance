package com.ct.design.structure.proxy

import com.ct.design.vo.fruit.Fruit
import com.ct.design.vo.shop.FruitShop

class OverseasFruitShop : FruitShop() {
    override fun sellFruit(): Fruit {
        return Avocado()
    }
}

class Avocado : Fruit() {
    override fun getName(): String = "牛油果"

    override fun getPrice(): Double = 100.0

}