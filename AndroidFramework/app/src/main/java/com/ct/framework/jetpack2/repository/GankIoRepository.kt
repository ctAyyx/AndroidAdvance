package com.ct.framework.jetpack2.repository

import androidx.lifecycle.LiveData
import com.ct.framework.jetpack2.base.*

import com.ct.framework.jetpack2.db.dao.GanKIoDao
import com.ct.framework.jetpack2.dto.Detail
import com.ct.framework.jetpack2.fetch.BaseResponse
import com.ct.framework.jetpack2.fetch.GnakIoServiceApi
import com.ct.framework.jetpack2.vo.DetailVo

/**
 *
 * */
class GankIoRepository(
    private val appExecutors: AppExecutors,
    private val dao: GanKIoDao,
    private val serviceApi: GnakIoServiceApi
) {

    /**
     * 获取 文章详情
     * */
    fun getDetail(id: String): LiveData<Resource<DetailVo>> {
        val request = object : NetworkBoundResource<DetailVo, BaseResponse<Detail>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<BaseResponse<Detail>>> {
                return serviceApi.getDetail(id)
            }

            override fun processResponse(response: ApiSuccessResponse<BaseResponse<Detail>>): DetailVo {
                return response.data.data.run {
                    DetailVo(
                        id = id,
                        author = author,
                        image = images[0],
                        time = updatedAt,
                        type = type
                    )
                }
            }

            override fun useDB() = true

            override fun loadFromDb(): LiveData<DetailVo> {

                return dao.getDetailById(id)
            }

            override fun shouldFetch(data: DetailVo?): Boolean {
                return data == null || data.type == "Girl"
            }

            override fun saveCallResult(data: DetailVo) {
                dao.insertDetail(data)
            }

        }

        return request.asLiveData()

    }


    fun getDetailWithStatus(id: String): ResourceStatus<DetailVo> {
        val request = object : NetworkStatusResource<DetailVo, BaseResponse<Detail>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<BaseResponse<Detail>>> {
                return serviceApi.getDetail(id)
            }

            override fun processResponse(response: ApiSuccessResponse<BaseResponse<Detail>>): DetailVo {
                return response.data.data.run {
                    DetailVo(
                        id = id,
                        author = author,
                        image = images[0],
                        time = updatedAt,
                        type = type
                    )
                }
            }
        }


        return ResourceStatus(
            data = request.asLiveData(),
            networkState = request.status,
            retry = { request.retryAllFail() }
        )

    }

}