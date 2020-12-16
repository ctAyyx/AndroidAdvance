package com.ct.framework.hilt.vm

import android.content.Context
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.qualifiers.ActivityContext

class GankViewModel @ViewModelInject constructor(
    private val repository: GankRepository,
    @ActivityContext
    private val context: Context,
    @Assisted
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getDetail(id: String) {
        Log.e("TAG", "获取数据:${repository}")
    }
}