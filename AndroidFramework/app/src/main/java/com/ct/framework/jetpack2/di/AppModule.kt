package com.ct.framework.jetpack2.di

import com.ct.framework.jetpack2.fetch.GnakIoServiceApi
import com.ct.framework.jetpack2.fetch.LiveDataCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppModule {

    companion object {
        private val appModule = AppModule()
        fun getServiceApi() = appModule.provideService(appModule.provideClient())
    }

    fun provideService(client: OkHttpClient): GnakIoServiceApi {
        return Retrofit.Builder()
            .baseUrl("https://gank.io")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)
            .build()
            .create(GnakIoServiceApi::class.java)
    }


    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            //.addNetworkInterceptor(HeaderInterceptor())
            //.addInterceptor(CInterceptor())
            .connectTimeout(15_000L, TimeUnit.MILLISECONDS)
            .writeTimeout(15_000L, TimeUnit.MILLISECONDS)
            .readTimeout(15_000L, TimeUnit.MILLISECONDS)
            .build()
    }
}