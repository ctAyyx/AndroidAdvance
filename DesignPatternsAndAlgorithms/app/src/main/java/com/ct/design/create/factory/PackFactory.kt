package com.ct.design.create.factory

import com.ct.design.vo.pack.ApplePack
import com.ct.design.vo.pack.Pack
import com.ct.design.vo.pack.PearPack

interface PackFactory {

    fun getPack(): Pack
}

class ApplePackFactory : PackFactory {
    override fun getPack(): Pack = ApplePack()

}

class PearPackFactory : PackFactory {
    override fun getPack(): Pack = PearPack()

}