package ct.com.ui.course10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ct.com.ui.R

class Course10RvAdapter : RecyclerView.Adapter<Course10RvAdapter.ViewHolder>() {

    private val mList = mutableListOf<Course10Bean>().apply {
        add(Course10Bean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598520484849&di=63db8e84527df19a402f7ba09d2c9a36&imgtype=0&src=http%3A%2F%2Fbos.pgzs.com%2Frbpiczy%2FWallpaper%2F2013%2F2%2F21%2F924e9018181449b78f6659d050079fee-3.jpg"))
        add(Course10Bean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598520484849&di=6bbf9c0070ca214101f37c3916e8709b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201803%2F10%2F20180310224507_lqocp.jpg"))
        add(Course10Bean("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2220381114,787577306&fm=26&gp=0.jpg"))
        add(Course10Bean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598520484847&di=cf5fb4cb9391fe3b1f616bb3b5283a17&imgtype=0&src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20180706%2F05%2F1530827254-RyAnHKmUia.jpg"))
        add(Course10Bean("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1598520484724&di=47ac721b9bf98b4b758567d2bf981c75&imgtype=0&src=http%3A%2F%2Ftupian.qqjay.com%2Fu%2F2017%2F1221%2F2_144011_10.jpg"))
    }


    override fun getItemCount() = mList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_course10, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = mList[position]

        holder.bindData(model)

    }


    fun toBottom(position: Int) {

        val model = mList.removeAt(position)
        mList.add(0,model)
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