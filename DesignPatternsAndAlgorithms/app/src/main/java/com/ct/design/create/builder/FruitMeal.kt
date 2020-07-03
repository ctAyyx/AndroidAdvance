package com.ct.design.create.builder

import com.ct.design.vo.fruit.Fruit
import com.ct.design.vo.pack.Pack

/**
 * 水果套餐类
 *
 * 套餐一: 一个苹果用苹果盒子 一个梨子用梨子盒子 折扣10
 * 套餐二: 二个苹果用苹果盒子 五个梨子用梨子盒子 折扣10
 *
 * */
class FruitMeal {

    private val items = mutableListOf<MealItem>()

    private var discount = 0.0

    fun addFruit(item: MealItem) {
        items.add(item)
    }


    fun getPrice(): Double {

        var price = 0.0
        for (item in items)
            price += item.fruit.getPrice()

        return price - discount
    }
}

data class MealItem(val fruit: Fruit, val pack: Pack)
