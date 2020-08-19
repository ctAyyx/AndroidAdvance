package ct.com.ui.course03

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    //================准备开始嵌套滚动 DOWN事件===================

    override fun startNestedScroll(axes: Int): Boolean {
        Log.e("MyRecyclerView", "startNestedScroll:axes$axes")
        return super.startNestedScroll(axes)
    }

    override fun startNestedScroll(axes: Int, type: Int): Boolean {
        Log.e("MyRecyclerView", "startNestedScroll:axes$axes -- type:$type")
        return super.startNestedScroll(axes, type)
    }

    //================准备分发嵌套滚动 MOVE事件=====================

    /**
     * 开始嵌套滚动之前
     * */
    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

}