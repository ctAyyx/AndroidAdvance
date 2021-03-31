/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ct.framework.adapter.base

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ct.framework.BR


/**
 * 基础列表
 * */
abstract class DataBoundListAdapter<T, V : ViewDataBinding>(
    // appExecutors:  Executors,
    diffCallback: DiffUtil.ItemCallback<T> = CommonDiff()
) : ListAdapter<T, DataBoundViewHolder<V>>(
    AsyncDifferConfig.Builder<T>(diffCallback)
        //.setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        val binding = createBinding(parent, viewType)
        return DataBoundViewHolder(binding)
    }

    private fun createBinding(parent: ViewGroup, viewType: Int): V =
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutId(viewType),
            parent,
            false
        )

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        holder.binding.setVariable(BR.model, getItem(position))
        holder.binding.setVariable(
            BR.onItemClick,
            onItemClick(holder.binding, position, getItem(position))
        )
        bind(holder.binding, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    protected abstract fun getLayoutId(viewType: Int): Int
    protected abstract fun bind(binding: V, item: T, position: Int)
    open fun onItemClick(binding: V, position: Int, model: T) = View.OnClickListener { }

    class CommonDiff<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem

        override fun areContentsTheSame(oldItem: T, newItem: T) =
            oldItem.hashCode() == newItem.hashCode()

    }
}


