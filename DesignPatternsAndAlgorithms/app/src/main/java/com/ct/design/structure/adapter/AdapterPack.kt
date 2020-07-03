package com.ct.design.structure.adapter

import com.ct.design.vo.pack.ApplePack
import com.ct.design.vo.pack.Pack
import com.ct.design.vo.pack.PearPack

class AdapterPack : ApplePack(), Pack {

    private val pack = PearPack()
    override fun packName(): String {
        return pack.packName()
    }


}