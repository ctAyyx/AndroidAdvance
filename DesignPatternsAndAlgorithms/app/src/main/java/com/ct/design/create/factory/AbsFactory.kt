package com.ct.design.create.factory

import com.ct.design.vo.fruit.Apple
import com.ct.design.vo.fruit.Fruit
import com.ct.design.vo.fruit.Pear
import com.ct.design.vo.pack.ApplePack
import com.ct.design.vo.pack.Pack
import com.ct.design.vo.pack.PearPack


interface FruitPackFactory {

    fun getFruit(): Fruit

    fun getPack(): Pack
}

class AppleFruitPackFactory : FruitPackFactory {


    override fun getFruit(): Fruit = Apple()

    override fun getPack(): Pack = ApplePack()

}

class PearFruitPackFactory : FruitPackFactory {
    override fun getFruit(): Fruit = Pear()

    override fun getPack(): Pack = PearPack()

}