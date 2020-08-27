package ct.com.ui.course03

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ct.com.ui.R

/**
 * TIME : 2020/8/20 0020
 * AUTHOR : CT
 * DESC :
 */
class RvAdapter : RecyclerView.Adapter<RvAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_list, parent, false
        )
    )

    override fun getItemCount() = 30

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}