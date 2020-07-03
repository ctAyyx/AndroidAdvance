package com.ct.design.structure.composite

/**
 * 代表一个地区
 * */
interface AreaNode {
    fun addNextArea(areaNode: AreaNode)

    fun getAreaName(): String

}


class ComponentAreaNode(private val areaName: String) : AreaNode {

    private val nextAreas = mutableListOf<AreaNode>()
    override fun addNextArea(areaNode: AreaNode) {
        nextAreas.add(areaNode)
    }

    fun getArea(): String {
        var areaStr = getAreaName()
        for (area in nextAreas)
            areaStr += "-${area.getAreaName()}"
        return areaStr
    }


    override fun getAreaName(): String = areaName

}