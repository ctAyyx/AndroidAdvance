package ct.com.ui.course04.demo01

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import ct.com.ui.R

class LvAdapter(private val mList: List<String>) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        return convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_list, parent, false)

    }

    override fun getItem(position: Int): String = mList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = mList.size

    inner class ViewHolder {

    }
}