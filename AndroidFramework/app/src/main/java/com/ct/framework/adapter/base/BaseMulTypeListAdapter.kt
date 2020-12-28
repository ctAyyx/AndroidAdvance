package com.ct.framework.adapter.base

import androidx.databinding.ViewDataBinding


abstract class BaseMulTypeListAdapter<T> : DataBoundListAdapter<T, ViewDataBinding>() {

    override fun getItemViewType(position: Int): Int {
        return getMulTypeLayoutId(position)
    }

    override fun getLayoutId(viewType: Int): Int {

        return viewType
    }


    abstract fun getMulTypeLayoutId(position: Int): Int

}