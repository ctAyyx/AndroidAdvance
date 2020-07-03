package com.ct.design.vo.fruit

import com.ct.design.vo.fruit.Fruit

class Pear(private val price: Double = 0.0) : Fruit() {
    override fun getName(): String = "梨子"

    override fun getPrice(): Double = price
}