package com.ct.design.vo.fruit

class Apple(private val price: Double = 0.0) : Fruit() {


    override fun getName(): String = "苹果"

    override fun getPrice(): Double {
        return price
    }

}