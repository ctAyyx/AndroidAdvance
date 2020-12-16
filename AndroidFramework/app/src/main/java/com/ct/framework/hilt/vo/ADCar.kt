package com.ct.framework.hilt.vo

import javax.inject.Inject

class ADCar @Inject constructor() : Car {
    override fun getCarName(): String {
        return "奥迪车"
    }
}