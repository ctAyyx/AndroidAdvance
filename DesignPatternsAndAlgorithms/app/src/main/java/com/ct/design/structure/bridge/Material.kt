package com.ct.design.structure.bridge

/**
 * 材质
 * */
interface Material {

    fun getMaterial(): String
}

class PaperMaterial : Material {
    override fun getMaterial(): String = "纸质的"

}

class PlasticMaterial : Material {
    override fun getMaterial(): String = "塑料的"

}

class JuteMaterial : Material {
    override fun getMaterial(): String = "麻袋的"

}