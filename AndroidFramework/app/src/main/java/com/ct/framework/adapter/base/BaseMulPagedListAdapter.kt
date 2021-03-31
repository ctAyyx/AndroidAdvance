package com.ct.framework.adapter.base

import androidx.databinding.ViewDataBinding

/**
 * 分页库 多布局
 * */
abstract class BaseMulPagedListAdapter<T> : BasePagedListAdapter<T, ViewDataBinding>() {

    override fun getItemViewType(position: Int): Int {
        return getMulTypeLayoutId(position, getItem(position))
    }

    override fun getLayoutId(viewType: Int): Int {
        return viewType
    }

    abstract fun getMulTypeLayoutId(position: Int, model: T?): Int

}