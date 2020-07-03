package com.ct.design.structure.decorator

import com.ct.design.vo.fruit.Fruit
import com.ct.design.vo.pack.Pack

abstract class PackDecorator(private val fruit: Fruit) : Fruit() {

    override fun getName(): String = fruit.getName()

    override fun getPrice(): Double = fruit.getPrice()


    override fun addPack(pack: Pack) {
        fruit.addPack(pack)
    }




}

