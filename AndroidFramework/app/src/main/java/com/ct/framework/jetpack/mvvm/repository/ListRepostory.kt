package com.ct.framework.jetpack.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ct.framework.jetpack.dto.*
import com.ct.framework.jetpack.mvvm.base.AppExecutors
import com.ct.framework.jetpack.mvvm.base.BaseResource
import com.ct.framework.jetpack.mvvm.base.NetworkBoundResource
import com.ct.framework.jetpack.mvvm.base.Resource
import com.ct.framework.jetpack.net.ServiceApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListRepository(private val serviceApi: ServiceApi) {

    fun getList(page: Int, pageSize: Int): LiveData<List<Category>> {
        val result = MutableLiveData<List<Category>>()
        val call = serviceApi.getGirlList(page, pageSize)
        call.enqueue(object : Callback<BaseResponse<List<Category>>> {
            override fun onFailure(call: Call<BaseResponse<List<Category>>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<BaseResponse<List<Category>>>,
                response: Response<BaseResponse<List<Category>>>
            ) {
                result.value = response.body()?.data
            }

        })

        return result
    }


    fun getGirlDetail(id: String): LiveData<Detail> {
        val result = MutableLiveData<Detail>()

        serviceApi.getGirlDetail(id).enqueue(object : Callback<BaseResponse<Detail>> {
            override fun onFailure(call: Call<BaseResponse<Detail>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<BaseResponse<Detail>>,
                response: Response<BaseResponse<Detail>>
            ) {
                result.value = response.body()?.data
            }
        })

        return result
    }


    /**
     * 下面的方法测试 封装的数据获取类
     * */
    fun getGirlDetail02(id: String, appExecutors: AppExecutors): LiveData<Resource<Detail>> {
        val request = object : NetworkBoundResource<Detail, BaseResponse<Detail>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<BaseResponse<Detail>>> {
                return serviceApi.getGirlDetail02(id)
            }

            override fun processResponse(response: ApiSuccessResponse<BaseResponse<Detail>>): Detail =
                response.data.data

        }

        return request.asLiveData()
    }


    fun getGirlDetail03(id: String, appExecutors: AppExecutors): LiveData<Resource<Detail>> {
        val request = object : NetworkBoundResource<Detail, Detail>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<Detail>> {
                return serviceApi.getGirlDetail03(id)
            }

            override fun processResponse(response: ApiSuccessResponse<Detail>): Detail {
                Log.e("TAG", "转换:${response}")
                return response.data
            }

        }
        return request.asLiveData()
    }

    fun getGirlDetail04(id: String): LiveData<Resource<Detail>> {
        val request = object : BaseResource<Detail, Detail>() {
            override fun processResponse(response: BaseResponse<Detail>): Detail {
                return response.data
            }

            override fun createCall(): LiveData<ApiResponse<BaseResponse<Detail>>> {
                return serviceApi.getGirlDetail02(id)
            }
        }

        return request.asLiveData()
    }


    /**************** 玩Android 数据测试 *****************/
    fun getWanChapterList(page:String,appExecutors: AppExecutors): LiveData<Resource<ChapterParent>> {
        val request = object :
            NetworkBoundResource<ChapterParent, BaseResponse2<ChapterParent>>(appExecutors = appExecutors) {
            override fun createCall(): LiveData<ApiResponse<BaseResponse2<ChapterParent>>> {
                return serviceApi.getChapterList("https://www.wanandroid.com/article/list/${page}/json")
            }

            override fun processResponse(response: ApiSuccessResponse<BaseResponse2<ChapterParent>>): ChapterParent {
                return response.data.data
            }
        }
        return request.asLiveData()
    }

}