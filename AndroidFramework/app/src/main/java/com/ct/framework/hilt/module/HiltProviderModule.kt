package com.ct.framework.hilt.module

import android.app.Application
import android.content.Context
import com.ct.framework.hilt.alias.CarAD
import com.ct.framework.hilt.alias.CarBM
import com.ct.framework.hilt.point.UserManager
import com.ct.framework.hilt.vo.ADCar
import com.ct.framework.hilt.vo.BMCar
import com.ct.framework.hilt.vo.Car
import com.ct.framework.hilt.vo.User
import com.ct.framework.jetpack2.base.AppExecutors
import com.ct.framework.jetpack2.fetch.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class HiltProviderModule {

    @Provides
    fun providerRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gank.io")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)
            .build()
    }

    @Provides
    fun providerOkClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15_000L, TimeUnit.MILLISECONDS)
            .writeTimeout(15_000L, TimeUnit.MILLISECONDS)
            .readTimeout(15_000L, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providerAppExecutors(): AppExecutors {
        return AppExecutors()
    }


    @Singleton
    @Provides
    @CarBM
    fun providerBMCar(): Car = BMCar()


    @Provides
    @Singleton
    @CarAD
    fun providerADCar(): Car = ADCar()


    @Provides
    @CarBM
    fun providerUser(@CarBM car: Car): User = User(car)

    @Provides
    @CarAD
    fun providerUser2(@CarAD car: Car): User = User(car)


    @Provides
    @Singleton
    fun providerUserManager(@ApplicationContext context: Context): UserManager =
        UserManager(context = context)

}