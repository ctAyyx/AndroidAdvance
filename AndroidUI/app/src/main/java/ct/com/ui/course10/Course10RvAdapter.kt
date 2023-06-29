package ct.com.ui.course10

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ct.com.ui.R

class Course10RvAdapter : RecyclerView.Adapter<Course10RvAdapter.ViewHolder>() {

    companion object {
        private const val DEBUG = true
        private const val TAG = "Course10RvAdapter"
    }


    private val mList = mutableListOf<Course10Bean>().apply {
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
        add(Course10Bean("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fpic%2F3%2Fb9%2F2c591299723.jpg%3Fdown&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1661416125&t=c684590b2241addbe24b7204211cf0f2"))
    }


    override fun getItemCount() = mList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.e(TAG, "onCreateViewHolder")
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_course10, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e(TAG, "onBindViewHolder")
        val model = mList[position]
        holder.bindData(model)

    }


    fun toBottom(position: Int) {
        val model = mList.removeAt(position)
        mList.add(0, model)
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val img by lazy { itemView.findViewById<ImageView>(R.id.img_itemCourse10) }


        fun bindData(model: Course10Bean) {
            Glide.with(itemView)
                .load(model.url)
                .into(img)
        }
    }


}