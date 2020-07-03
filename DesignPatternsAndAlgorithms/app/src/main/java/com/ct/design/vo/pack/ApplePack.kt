package com.ct.design.vo.pack

open class ApplePack : Pack {


    override val size: String = "中号"

    override val material: String = "纸质"

    override fun packName(): String = "苹果的包装盒"
}