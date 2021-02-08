package ct.com.ui.course13.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat

/**
 * 实现 NestedScrollingParent
 * */
class MyNestedParentLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), NestedScrollingParent {

    private val mParentHelper by lazy { NestedScrollingParentHelper(this) }

    //我们可以滚动的距离
    private var mHeadHeight = 0

    // 还可以滚动的距离
    private var mHasDy = 0


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        mHeadHeight = getChildAt(0).measuredHeight
    }

    /**
     *  开始嵌套滚动
     *
     *  @param axes 滚动的方向
     *  @return 是否处理嵌套滚动 true表示处理
     * */
    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    /**
     * 如果 处理嵌套滚动 这个方法会被回调
     * */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        mParentHelper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        //1.step 判断是否拥有可以滚动的距离
        val consumedY = mHeadHeight - scrollY
        if (dy > 0) {//向上滚动
            //当 滚动了的距离小于我们可以滚动的距离

            if (consumedY > dy) {
                consumed[1] += dy
            } else {
                consumed[1] += consumedY
            }
            scrollBy(0, consumed[1])
        }

    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        val oldScrollY = scrollY
        if (oldScrollY > 0) {
            //向下滚动
            scrollBy(0, dyUnconsumed)
        }

    }


    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return false
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return false
    }


    override fun onStopNestedScroll(target: View) {
        mParentHelper.onStopNestedScroll(target)
    }

    override fun getNestedScrollAxes(): Int {
        return mParentHelper.nestedScrollAxes
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }
}