package ct.com.ui.course10

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sqrt

class OverlayTouchHelper(private val adapter: Course10RvAdapter) : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.START or ItemTouchHelper.END or ItemTouchHelper.DOWN or ItemTouchHelper.UP
) {
    companion object {
        private const val TAG = "OverlayTouchHelper"
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.e(TAG, "===>>>onSwiped")
        adapter.toBottom(viewHolder.adapterPosition)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.33333f
    }
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)


        val distance = sqrt(dX * dX + dY * dY)
        // 临界值比例
        var fraction = distance / (recyclerView.width * 0.33333f)
        if (fraction > 1.0)
            fraction = 1.0f
        val itemCount = recyclerView.childCount
        Log.e(TAG, "===>>>fraction:$fraction itemCount:$itemCount")
        for (i in 0 until (itemCount - 1)) {
            val childView = recyclerView.getChildAt(i)
            val level = itemCount - i - 1
            if (level > 0 && level < OverlayLayoutManager.MAX_OVER_SIZE - 1) {
                childView.translationY =
                    (OverlayLayoutManager.TRANS_Y_GAP * level - fraction * OverlayLayoutManager.TRANS_Y_GAP).toFloat()
                childView.scaleX =
                    (1 - OverlayLayoutManager.SCALE_GAP * level + fraction * OverlayLayoutManager.SCALE_GAP).toFloat()
                childView.scaleY =
                    (1 - OverlayLayoutManager.SCALE_GAP * level + fraction * OverlayLayoutManager.SCALE_GAP).toFloat()
            }
        }

    }
}