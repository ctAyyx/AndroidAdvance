package com.ct.design.create

import com.ct.design.create.factory.*


fun main() {

    //简单工厂模式
    // simpleFactory()
    //抽象方法模式
    //adsMethodFactory()
    //抽象工厂模式
    absFactory()

    //单例模式 不用写了

    //建造者模式


}


/**
 * 简单工厂模式(不是23种设计模式中的)
 *
 * 将 对象的创建和客户端隔离
 * 缺点:
 * 每次新加产品都要去修改SimpleFruitFactory,违反了开闭原则
 * 而且SimpleFruitFactory管理了太多的对象单一职责
 * */
private fun simpleFactory() {
    val factory = SimpleFruitFactory()
    //获取苹果
    val apple = factory.getFruit(APPLE)
    println("采摘的水果是:${apple.getName()}")
    //获取梨子
    val pear = factory.getFruit(PEAR)
    println("采摘的水果是:${pear.getName()}")
}

/**
 * 工厂方法模式
 *
 * 为了解决简单工程模式的问题 将工厂抽象出来 每个对象对应一个工厂,将产品的创建打碎 由每个对应的工厂创建
 *
 * 缺点:随着产品对象的增加 会出现很多产品的工厂，而且无法解决多个产品接口的问题
 */

private fun adsMethodFactory() {

    val appleFactory: FruitFactory = AppleFactory()
    val pearFactory: FruitFactory = PearFactory()

    val apple = appleFactory.getFruit()
    val pear = pearFactory.getFruit()

    println("采摘的水果是:${apple.getName()} -- ${pear.getName()}")
}


/**
 * 抽象工厂模式
 *
 * 需求:在采摘水果的同时 需要获取一个水果的盒子
 * */
private fun absFactory() {


//    val appleFactory: FruitFactory = AppleFactory()
//    val applePackFactory: PackFactory = ApplePackFactory()
//    val pearFactory: FruitFactory = PearFactory()
//    val pearPackFactory: PackFactory = PearPackFactory()
//
//    val apple = appleFactory.getFruit()
//    val applePack = applePackFactory.getPack()
//
//    val pear = pearFactory.getFruit()
//    val pearPack = pearPackFactory.getPack()
//
//    println("采摘的水果:${apple.getName()},使用的盒子:${applePack.packName()}")
//    println("采摘的水果:${pear.getName()},使用的盒子:${pearPack.packName()}")
//    //出现 水果 和 包装盒关系不明确
//    println("采摘的水果:${apple.getName()},使用的盒子:${pearPack.packName()}")


    val applePackFactory: FruitPackFactory = AppleFruitPackFactory()
    val pearPackFactory: FruitPackFactory = PearFruitPackFactory()

    println(
        "采摘的水果:${applePackFactory.getFruit().getName()},使用的盒子:${applePackFactory.getPack()
            .packName()}"
    )


    println(
        "采摘的水果:${pearPackFactory.getFruit().getName()},使用的盒子:${pearPackFactory.getPack()
            .packName()}"
    )

}


/**
 * 建造者模式
 *
 * 如果一个对象的创建是十分复杂的 就可以使用builder模式将其拆分
 *
 * 需求:现在水果店推出套餐,多种水果一起会有打折优惠
 */
private fun builderPattern() {



}