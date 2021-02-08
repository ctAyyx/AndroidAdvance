package ct.com.ui.course13.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat

class MyNestedChildView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), NestedScrollingChild {

    private val mChildHelper by lazy { NestedScrollingChildHelper(this) }

    override fun onFinishInflate() {
        super.onFinishInflate()
        isNestedScrollingEnabled = true
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mChildHelper.isNestedScrollingEnabled = enabled
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return mChildHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return mChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        mChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return mChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

    private var mLastTouchX = 0F
    private var mLastTouchY = 0F
    private val mReusableIntPair = IntArray(2)
    private val mNestedOffsets = IntArray(2)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastTouchX = event.x + 0.5f
                mLastTouchY = event.y + 0.5f
                //开始分发嵌套滚动事件
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)

            }
            MotionEvent.ACTION_MOVE -> {
                var dx = mLastTouchX - event.x
                var dy = mLastTouchY - event.y
                mReusableIntPair[0] = 0
                mReusableIntPair[1] = 0
                dispatchNestedPreScroll(dx.toInt(), dy.toInt(), mReusableIntPair, mNestedOffsets)

                dx -= mReusableIntPair[0]
                dy -= mReusableIntPair[1]
                parent.requestDisallowInterceptTouchEvent(true)
                mLastTouchX = event.x + 0.5f
                mLastTouchY = event.y + 0.5f
                scrollByInternal(0, dy.toInt(), event)
            }
            MotionEvent.ACTION_UP -> {
                stopNestedScroll()
            }
        }


        return true
    }


    private fun scrollByInternal(x: Int, y: Int, ev: MotionEvent?): Boolean {
        var unconsumedX = 0
        var unconsumedY = 0
        var consumedX = 0
        var consumedY = 0

        scrollStep(x, y, mReusableIntPair)
        consumedY = mReusableIntPair[1]
        unconsumedY = y - consumedY
        mReusableIntPair[0] = 0
        mReusableIntPair[1] = 0
        return dispatchNestedScroll(
            consumedX, consumedY, unconsumedX, unconsumedY, mReusableIntPair
        )
    }

    private fun scrollStep(x: Int, y: Int, mReusableIntPair: IntArray) {
        val oldScrollY = scrollY

        if (y < 0) { //向下滚动
            if (oldScrollY > 0) {
                if (oldScrollY + y > 0)
                    mReusableIntPair[1] = y
                else
                    mReusableIntPair[1] = -oldScrollY
                scrollBy(0,mReusableIntPair[1])
            }


        } else {//向上滚动

            mReusableIntPair[1] = y
            scrollBy(0, y)
        }
    }


}