package com.ct.framework.jetpack2.base.over

import com.ct.framework.jetpack2.base.NetworkState

abstract class NetworkSource<ResultType, RequestType> :
    BaseFetchResource<ResultType, RequestType>() {

    override suspend fun fetch() {
        setStatus(NetworkState.LOADING)
        try {
            val apiSource = createCall()
            val newData = processResponse(apiSource)
            setStatus(NetworkState.SUCCESS)
            setValue(newData)
        } catch (e: Exception) {
            retry = {
                fetch()
            }
            setValue(null)
            setStatus(NetworkState.error(e.message ?: "Unknown Error"))
        }
    }
}