package com.ct.design.structure

import com.ct.design.create.factory.AppleFactory
import com.ct.design.create.factory.ApplePackFactory
import com.ct.design.structure.adapter.AdapterPack
import com.ct.design.structure.bridge.BigSize
import com.ct.design.structure.bridge.PlasticMaterial
import com.ct.design.structure.composite.AreaNode
import com.ct.design.structure.composite.ComponentAreaNode
import com.ct.design.structure.decorator.ReinforcePackDecorator
import com.ct.design.structure.decorator.SignPackDecorator
import com.ct.design.structure.proxy.FruitShopProxy
import com.ct.design.structure.proxy.OverseasFruitShop
import com.ct.design.vo.pack.ApplePack
import com.ct.design.vo.pack.OrangePack

fun main() {
    //适配器模式
    //adapter()

    //桥接模式
    // bridge()

    //装饰器模式
    //decorator()

    //代理模式
    //proxy()

    //组合模式
    composite()


}

/**
 * 适配器模式
 *
 * 适配器模式（Adapter Pattern）是作为两个不兼容的接口之间的桥梁
 * 意图：将一个类的接口转换成客户希望的另外一个接口。适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作。
 * 主要解决：主要解决在软件系统中，常常要将一些"现存的对象"放到新的环境中，而新环境要求的接口是现对象不能满足的。
 *
 * 需求: 现在包装苹果的盒子没有了 需要使用包装梨子的盒子.苹果是无法直接使用装梨子的盒子的
 * */
private fun adapter() {

    val apple = AppleFactory().getFruit()

    val applePack: ApplePack = AdapterPack()

    println("水果:${apple.getName()} -- 盒子:${applePack.packName()}")
}

/**
 * 桥接模式
 * 将抽象部分与实现部分分离，使它们都可以独立的变化，实现系统可能有多个角度分类，每一种角度都可能变化。
 * 意图：将抽象部分与实现部分分离，使它们都可以独立的变化
 * 主要解决：在有多种可能会变化的情况下，用继承会造成类爆炸问题，扩展起来不灵活。
 *
 * 需求:现在包装盒有 大小 和 材质的属性了
 *      * 纸质  塑料 麻袋 竹子
 *     大  1     2   3   4
 *     中  5     6   7    8
 *     小  9     10  11   12
 *     如果使用继承的话 要出现12个类
 *
 * 缺点：桥接模式的引入会增加系统的理解与设计难度，由于聚合关联关系建立在抽象层，要求开发者针对抽象进行设计与编程
 * */
private fun bridge() {

    val size = BigSize()
    val material = PlasticMaterial()
    val orangePack = OrangePack(size, material)
    println("水果盒子:${orangePack.packName()} -- ${orangePack.material} -- ${orangePack.size}")

}

/**
 * 装饰器模式
 * 装饰器模式（Decorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构。
 * 意图：动态地给一个对象添加一些额外的职责。就增加功能来说，装饰器模式相比生成子类更为灵活。
 * 主要解决：一般的，我们为了扩展一个类经常使用继承方式实现，由于继承为类引入静态特征，并且随着扩展功能的增多，子类会很膨胀。
 *
 * 需求:现在在水果打包的时候 需要添加防伪 加固 快件的功能
 * */
private fun decorator() {

    val pack = ApplePackFactory().getPack()
    var apple = AppleFactory().getFruit()


    apple = SignPackDecorator(apple)
    apple = ReinforcePackDecorator(apple)
    apple.addPack(pack)


}

/**
 * 代理模式
 * 在代理模式（Proxy Pattern）中，一个类代表另一个类的功能
 * 意图：为其他对象提供一种代理以控制对这个对象的访问
 * 主要解决：在直接访问对象时带来的问题，比如说：要访问的对象在远程的机器上。
 * 在面向对象系统中，有些对象由于某些原因（比如对象创建开销很大，或者某些操作需要安全控制，
 * 或者需要进程外的访问），直接访问会给使用者或者系统结构带来很多麻烦，我们可以在访问此对象时加上一个对此对象的访问层。
 *
 * 需求:水果销售不仅可以销售苹果 橘子  还能销售国外的水果
 *  客户下单 --->接受订单 --------------->送货上门
 *                |                 |
 *                |                 |
 *             第三方接单-->采购-->发货给水果店
 * */
private fun proxy() {

    //用户无法直接购买国外水果 可以通过我们的商店购买
    val fruitShop = FruitShopProxy()
    println("购买的水果:${fruitShop.sellFruit().getName()}")
}

/**
 * 组合模式
 * 组合模式（Composite Pattern），又叫部分整体模式，是用于把一组相似的对象当作一个单一的对象
 * 主要解决：它在我们树型结构的问题中，模糊了简单元素和复杂元素的概念，客户程序可以像处理简单元素一样来处理复杂元素，从而使得客户程序与复杂元素的内部结构解耦
 *
 * 需求:现在水果商店有了配送功能,可以向全国配送水果
 *   东北
 *   湖南
 *   四川
 *    |---都江堰
 *        重庆
 *        成都
 *          |--武侯区
 *             金牛区
 *             双流区
 *                |--东升镇
 * */
private fun composite() {

    //现在想配送水果到 四川 成都 双流 东升

    val node01 = ComponentAreaNode("东升")
    val node02 = ComponentAreaNode("双流")
    val node03 = ComponentAreaNode("成都")
    val node04 = ComponentAreaNode("四川")

    node04.addNextArea(node03)
    node04.addNextArea(node02)
    node04.addNextArea(node01)

    println("配送地址:${node04.getArea()}")

}