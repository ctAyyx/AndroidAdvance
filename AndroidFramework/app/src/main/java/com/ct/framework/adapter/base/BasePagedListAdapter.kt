package com.ct.framework.adapter.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil


abstract class BasePagedListAdapter<T, V : ViewDataBinding>(diffCallback: DiffUtil.ItemCallback<T> = CommonDiff()) :
    PagedListAdapter<T, BasePagedListViewHolder<V>>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasePagedListViewHolder<V> {
        val binding = createBinding(parent, viewType)
        return BasePagedListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasePagedListViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position))
    }

    private fun createBinding(parent: ViewGroup, viewType: Int): V = DataBindingUtil.inflate<V>(
        LayoutInflater.from(parent.context),
        getLayoutId(viewType),
        parent,
        false
    )


    abstract fun getLayoutId(viewType: Int): Int
    abstract fun bind(binding: V, item: T?)

    class CommonDiff<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem

        override fun areContentsTheSame(oldItem: T, newItem: T) =
            oldItem.hashCode() == newItem.hashCode()

    }
}