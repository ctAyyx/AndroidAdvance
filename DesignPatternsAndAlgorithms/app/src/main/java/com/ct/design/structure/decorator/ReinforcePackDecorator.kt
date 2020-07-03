package com.ct.design.structure.decorator

import com.ct.design.vo.fruit.Fruit
import com.ct.design.vo.pack.Pack

class ReinforcePackDecorator(val fruit: Fruit) : PackDecorator(fruit) {
    override fun addPack(pack: Pack) {
        super.addPack(pack)
        println("给产品加固")
    }
}