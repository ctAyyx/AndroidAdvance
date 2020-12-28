package com.ct.paging3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ct.paging3.R
import com.ct.paging3.dto.Category
import com.ct.paging3.vo.GirlList

class CategoryAdapter :
    PagingDataAdapter<GirlList, CategoryAdapter.CategoryViewHolder>(CategoryDiff()) {


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_girl_list, parent, false)
        )
    }


    class CategoryDiff : DiffUtil.ItemCallback<GirlList>() {
        override fun areItemsTheSame(oldItem: GirlList, newItem: GirlList): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GirlList, newItem: GirlList): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class CategoryViewHolder(private val root: View) : RecyclerView.ViewHolder(root) {

        private var tvTitle: AppCompatTextView =
            root.findViewById<AppCompatTextView>(R.id.tv_itemGirlList_title)

        private val tvDesc: AppCompatTextView = root.findViewById(R.id.tv_itemGirlList_desc)

        private val img: AppCompatImageView = root.findViewById(R.id.img_itemGirlList)

        fun bind(model: GirlList) {
            tvTitle.text = model.title
            tvDesc.text = model.id
            Glide.with(img).load(model.imgUrl).into(img)
        }

    }


}