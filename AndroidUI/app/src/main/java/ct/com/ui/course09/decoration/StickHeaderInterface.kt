package ct.com.ui.course09.decoration

import androidx.recyclerview.widget.RecyclerView

interface StickHeaderInterface {

    fun isHeader(position: Int, recyclerView: RecyclerView): Boolean
    fun providerHeaderModel(position: Int): String
}
