package com.ct.framework.jetpack.mvvm.base

import android.util.Log
import androidx.lifecycle.LiveData
import com.ct.framework.jetpack.dto.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiResponse<R>>> {


    override fun responseType(): Type = responseType

    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        Log.e("TAG", "传入的responseType:$responseType")
        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onFailure(call: Call<R>, t: Throwable) {
                            Log.e("TAG", "数据请求失败:$t")
                            postValue(ApiResponse.Companion.create(t))
                        }

                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            Log.e("TAG", "数据请求完成:${response.code()}")
                            postValue(ApiResponse.Companion.create(response))
                        }

                    })
                }
            }
        }
    }
}