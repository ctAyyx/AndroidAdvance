package ct.com.ui.course10

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class OverlayTouchHelper(private val adapter: Course10RvAdapter) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.START or ItemTouchHelper.END or ItemTouchHelper.DOWN or ItemTouchHelper.UP
) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.toBottom(viewHolder.adapterPosition)
    }
}