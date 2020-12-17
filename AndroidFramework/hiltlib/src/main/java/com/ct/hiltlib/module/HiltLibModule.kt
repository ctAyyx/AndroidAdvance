package com.ct.hiltlib.module

import com.ct.hiltlib.vo.Contacts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class HiltLibModule {

    @Provides
    fun providerContacts(): Contacts = Contacts("测试Hilt模块")
}