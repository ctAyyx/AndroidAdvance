package com.ct.framework.jetpack.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ct.framework.BR
import com.ct.framework.databinding.ItemGirlListBinding
import com.ct.framework.jetpack.vo.GirlList

/**
 * 使用 分页库显示列表数据
 * */
class RvCategoryAdapter : PagedListAdapter<GirlList, RvCategoryAdapter.ViewHolder>(DIFF()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemGirlListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }


    inner class ViewHolder(private val binding: ItemGirlListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: GirlList) {
            binding.model = model
        }
    }

    class DIFF : DiffUtil.ItemCallback<GirlList>() {
        override fun areItemsTheSame(oldItem: GirlList, newItem: GirlList): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: GirlList, newItem: GirlList): Boolean =
            oldItem.id == newItem.id
    }
}