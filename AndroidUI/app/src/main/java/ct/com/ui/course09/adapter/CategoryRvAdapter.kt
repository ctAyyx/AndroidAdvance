package ct.com.ui.course09.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ct.com.ui.R
import ct.com.ui.course09.decoration.StickHeaderInterface
import ct.com.ui.course09.vo.Category

class CategoryRvAdapter : RecyclerView.Adapter<CategoryRvAdapter.ViewHolder>(),
    StickHeaderInterface {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val img by lazy { itemView.findViewById<ImageView>(R.id.img_itemList) }
        private val tv by lazy { itemView.findViewById<AppCompatTextView>(R.id.tv_itemList) }


        fun bindData(model: Category) {
            Glide.with(itemView)
                .load(model.url)
                .into(img)
            tv.text = model.name
        }
    }

    private val mList = mutableListOf<Category>()

    fun submit(list: List<Category>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list, parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(mList[position])
    }

    override fun getItemCount() = mList.size
    override fun isHeader(position: Int, recyclerView: RecyclerView): Boolean {
        return position % 15 == 0
    }

    override fun providerHeaderModel(position: Int): String {
        return "这是头部${position/15}"
    }

}