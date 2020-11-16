package com.ct.framework.jetpack.mvvm.paging

import android.util.Log
import androidx.paging.PagedList

class BdBoundaryCallback<T>() : PagedList.BoundaryCallback<T>() {
    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        Log.e("TAG", "onZeroItemsLoaded")
    }

    override fun onItemAtEndLoaded(itemAtEnd: T) {
        super.onItemAtEndLoaded(itemAtEnd)
        Log.e("TAG", "onItemAtEndLoaded")
    }

    override fun onItemAtFrontLoaded(itemAtFront: T) {
        super.onItemAtFrontLoaded(itemAtFront)
        Log.e("TAG", "onItemAtFrontLoaded")
    }
}

interface OnDBCallback {
    fun onEndData()
}