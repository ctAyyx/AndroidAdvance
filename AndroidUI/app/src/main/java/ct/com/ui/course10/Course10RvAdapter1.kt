package ct.com.ui.course10

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ct.com.ui.R

class Course10RvAdapter1 : RecyclerView.Adapter<Course10RvAdapter1.ViewHolder>() {

    companion object {
        private const val DEBUG = true
        private const val TAG = "Course10RvAdapter"
    }

    private var count = 0


    override fun getItemCount() = 20

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Log.e(TAG, "onCreateViewHolder:$count")
        count++
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_course10_1, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.e(TAG, "onBindViewHolder")
        holder.bindData(Course10Bean("", title = "标题:$position", position = position))

    }


    class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        fun bindData(model: Course10Bean) {
            val tvTitle = itemView.findViewById<AppCompatTextView>(R.id.tv_itemCourse10)
            tvTitle.text = model.title
            tvTitle.setBackgroundColor(
                if (model.position % 2 == 0) Color.parseColor("#7F7F7F") else Color.parseColor(
                    "#F7F0F0"
                )
            )
        }
    }


}