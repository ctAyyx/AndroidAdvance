package com.ct.framework.jetpack.mvvm.base

import com.ct.framework.jetpack.dto.ApiSuccessResponse
import com.ct.framework.jetpack.dto.BaseResponse

abstract class BaseResource<ResultType, RequestType> :
    NetworkBoundResource<ResultType, BaseResponse<RequestType>>(
        AppExecutors()
    ) {
    override fun processResponse(response: ApiSuccessResponse<BaseResponse<RequestType>>): ResultType =
        processResponse(response.data)

    abstract fun processResponse(response: BaseResponse<RequestType>): ResultType

}