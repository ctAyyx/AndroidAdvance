package com.ct.framework.hilt.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton


/**
 * 这里使用 Binds 提供注入实体
 * 主要针对要注入的对象是接口 或者抽象类的时候
 *
 * */
@Module
@InstallIn(ActivityComponent::class)
abstract class HiltModule {

//
//    @Binds
//    abstract fun bindBMCar(car: BMCar): Car


//    @Binds
//    @ADCarAnno
//    abstract fun bindADCar(car: ADCar): Car



}