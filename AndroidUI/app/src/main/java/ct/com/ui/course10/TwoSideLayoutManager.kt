package ct.com.ui.course10

import android.util.Log
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max


class TwoSideLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    private var mSumDy = 0
    private var maxScrollDy = 0

    private val mOrientationHelper by lazy {
        OrientationHelper.createVerticalHelper(this)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
Log.e("TAG","onLayoutChildren $state")
        val itemCount = itemCount
        //step1 回收当前View
        detachAndScrapAttachedViews(recycler)
        val focusedView = focusedChild
        var position = 0
        if (focusedView != null) {
            position = getPosition(focusedView)
            Log.e("TAG", "$focusedView ===>$position ")
        }


        // 获取剩余空间
        var mAvailable = mOrientationHelper.endAfterPadding
        while (mAvailable > 0) {
            //step2 获取或创建ViewHolder 获取ItemView
            val itemView = recycler.getViewForPosition(position)
            // step3 添加ItemView
            addView(itemView)
            //step4 测量ItemView
            measureChildWithMargins(itemView, 0, 0)
            //step5 布局ItemView
            val mConsumed = mOrientationHelper.getDecoratedMeasurement(itemView)
            val left = 0
            val top = position * getDecoratedMeasuredHeight(itemView)
            val right = left + getDecoratedMeasuredWidth(itemView)
            val bottom = top + getDecoratedMeasuredHeight(itemView)
            layoutDecorated(itemView, left, top, right, bottom)
            maxScrollDy = max(maxScrollDy, bottom)
            mAvailable -= mConsumed
            position += 1
        }

        // 这里需要减去布局高度 才是可滚动距离
        maxScrollDy = max(0, maxScrollDy - height)
    }

    override fun canScrollVertically(): Boolean {
        return true
    }


    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        //期望滚动的距离
        var travel = dy
        if (mSumDy + travel <= 0) {
            //已经达到顶部
            travel = -mSumDy
        }
        if (mSumDy + travel > maxScrollDy) {
            travel = maxScrollDy - mSumDy
        }

        mSumDy += travel
//        offsetChildrenVertical(-travel)
        mOrientationHelper.offsetChildren(-travel)
        return travel
    }

}

