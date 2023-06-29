package ct.com.ui.course03

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
 *  NestedScrollingChild 嵌套滑动子控件需要实现的接口
 *
 *  DOWN 事件
 *  1. startNestedScroll       子控件开始嵌套滑动 判断是否存在父控件会接受嵌套滚动事件
 *  MOVE 事件
 *  2. dispatchNestedPreScroll 子控件准备滚动前 分发事件给父控件 看父控件是否会消费事件
 *  3. dispatchNestedScroll    子控件的一个嵌套滚动事件 自己完成后 分发给父控件  看父控件是否会消费事件
 *  UP 事件
 *  4. dispatchNestedPreFling 子控件在消费Flying事件之前 分发给父控件看父控件是否会消费事件
 *  5. dispatchNestedFling    子控件在消费Flying事件之后 分发给父控件看父控件是否会消费事件
 *  6. stopNestedScroll       子控件停止嵌套滚动 并通知父控件
 *
 *
 *  7.isNestedScrollingEnabled 判断当前子控件是否开启嵌套滚动
 *  8.hasNestedScrollingParent 判断当前子控件是否有处理嵌套滚动的父控件
 *
 *
 * */
class MyRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.e("MyRecyclerView", "setNestedScrollingEnabled:enabled:$enabled")
        super.setNestedScrollingEnabled(enabled)

    }
    //================准备开始嵌套滚动 DOWN事件===================

//    override fun startNestedScroll(axes: Int): Boolean {
//        Log.e("MyRecyclerView", "startNestedScroll:axes$axes")
//        return super.startNestedScroll(axes)
//    }

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
        Log.e("MyRecyclerView", "dispatchNestedPreScroll:dx:$dx -- dy:$dy -- type:$type")
        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

//    override fun dispatchNestedPreScroll(
//        dx: Int,
//        dy: Int,
//        consumed: IntArray?,
//        offsetInWindow: IntArray?
//    ): Boolean {
//        Log.e(
//            "MyRecyclerView",
//            "dispatchNestedPreScroll:dx:$dx -- dy:$dy consumed:${consumed?.contentToString()} "
//        )
//        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
//    }

    //==============分发滚动事件 MOVE事件===================

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        Log.e(
            "MyRecyclerView",
            "dispatchNestedScroll:dxConsumed:$dxConsumed -- dyConsumed:$dyConsumed  -- dxUnconsumed:$dxUnconsumed --dyUnconsumed:$dyUnconsumed --offsetInWindow${offsetInWindow?.contentToString()} -- type:$type "
        )
        return super.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }


//    override fun dispatchNestedScroll(
//        dxConsumed: Int,
//        dyConsumed: Int,
//        dxUnconsumed: Int,
//        dyUnconsumed: Int,
//        offsetInWindow: IntArray?
//    ): Boolean {
//        Log.e(
//            "MyRecyclerView",
//            "dispatchNestedScroll:dxConsumed:$dxConsumed -- dyConsumed:$dyConsumed  -- dxUnconsumed:$dxUnconsumed --dyUnconsumed:$dyUnconsumed --offsetInWindow${offsetInWindow?.contentToString()} "
//        )
//        return super.dispatchNestedScroll(
//            dxConsumed,
//            dyConsumed,
//            dxUnconsumed,
//            dyUnconsumed,
//            offsetInWindow
//        )
//    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        Log.e(
            "MyRecyclerView",
            "dispatchNestedPreFling "
        )
        return super.dispatchNestedPreFling(velocityX, velocityY)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        Log.e(
            "MyRecyclerView",
            "dispatchNestedFling "
        )
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
    }


    //=====================停止嵌套滚动 ================
    override fun stopNestedScroll(type: Int) {
        Log.e("MyRecyclerView", "stopNestedScroll:type:$type ")
        super.stopNestedScroll(type)
    }

//    override fun stopNestedScroll() {
//        Log.e("MyRecyclerView", "stopNestedScroll ")
//        super.stopNestedScroll()
//    }


    internal class Log {
        companion object {
            fun e(tag: String, msg: String) {
                android.util.Log.e(tag, msg)
            }
        }
    }
}