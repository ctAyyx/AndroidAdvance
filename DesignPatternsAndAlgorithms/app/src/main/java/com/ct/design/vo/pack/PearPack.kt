package com.ct.design.vo.pack

class PearPack : Pack {
    override val size: String = "中号"

    override val material: String = "纸质"
    override fun packName(): String = "梨子的包装盒"
}