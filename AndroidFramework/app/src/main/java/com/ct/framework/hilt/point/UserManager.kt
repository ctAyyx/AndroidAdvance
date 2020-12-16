package com.ct.framework.hilt.point

import android.content.Context
import android.util.Log
import com.ct.framework.hilt.alias.CarAD
import com.ct.framework.hilt.vo.Car
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * 这个类 不是 hilt 支持的常见的Android类
 *
 * 如果需要在 Hilt 不支持的类中执行字段注入
 * 可以使用 @EntryPoint 注释创建入口点
 * */

class UserManager @Inject constructor(@ApplicationContext private val context: Context) {


    private val hiltEntryPoint = EntryPointAccessors.fromApplication(
        context,
        UserManagerProviderEntryPoint::class.java
    )


    //
    @EntryPoint
    @InstallIn(ApplicationComponent::class)
    interface UserManagerProviderEntryPoint {
        @CarAD
        fun car(): Car
    }


     var car: Car = hiltEntryPoint.car()

    //
    fun getMyCar() {

//        val car = hiltEntryPoint.car()
        Log.e("TAG", "我的汽车是:$car -- ${car.getCarName()}")
    }

}