package com.ct.design.vo.pack

import com.ct.design.structure.bridge.Material
import com.ct.design.structure.bridge.PaperMaterial
import com.ct.design.structure.bridge.Size
import com.ct.design.structure.bridge.SmallSize

class OrangePack(size1: Size = SmallSize(), material1: Material = PaperMaterial()) : Pack {

    override val size: String = size1.getSize()

    override val material: String = material1.getMaterial()

    override fun packName(): String ="橘子的盒子"

}