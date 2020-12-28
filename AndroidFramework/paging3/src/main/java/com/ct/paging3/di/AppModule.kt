package com.ct.paging3.di

import com.ct.paging3.net.ServiceApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppModule {

    companion object {
        private val appModule = AppModule()
        fun getServiceApi() = appModule.provideService(appModule.provideClient())
    }

    fun provideService(client: OkHttpClient): ServiceApi {
        return Retrofit.Builder()
            .baseUrl("https://gank.io")
            .addConverterFactory(GsonConverterFactory.create())

            .client(client)
            .build()
            .create(ServiceApi::class.java)
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