package com.ct.framework.hilt.vo

import javax.inject.Inject


class User @Inject constructor( private val car: Car) {
    //
    val name = "名字"

    fun print() = "姓名:$name 车子:${car.getCarName()} -- $car"
    //fun print() = "姓名:$name "

}