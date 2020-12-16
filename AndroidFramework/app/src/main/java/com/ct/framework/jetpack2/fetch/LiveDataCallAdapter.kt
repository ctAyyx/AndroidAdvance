package com.ct.framework.jetpack2.fetch

import android.util.Log
import androidx.lifecycle.LiveData
import com.ct.framework.jetpack2.base.ApiResponse
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
                            Log.e("TAG", "失败:${t.message}")
                            postValue(ApiResponse.create(t))
                        }

                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            val body = response.body()
                            postValue(ApiResponse.create(response))
                        }

                    })
                }
            }
        }
    }
}