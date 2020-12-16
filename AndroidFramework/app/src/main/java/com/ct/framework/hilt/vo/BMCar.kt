package com.ct.framework.hilt.vo

import javax.inject.Inject


class BMCar @Inject constructor() : Car {
    override fun getCarName(): String {
        return "宝马车"
    }
}