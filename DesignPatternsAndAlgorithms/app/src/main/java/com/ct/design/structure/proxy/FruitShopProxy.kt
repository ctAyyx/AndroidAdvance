package com.ct.design.structure.proxy

import com.ct.design.vo.fruit.Fruit
import com.ct.design.vo.shop.FruitShop

class FruitShopProxy() : FruitShop() {
    private val overseasFruitShop: FruitShop = OverseasFruitShop()

    override fun sellFruit(): Fruit {
        return overseasFruitShop.sellFruit()

    }
}