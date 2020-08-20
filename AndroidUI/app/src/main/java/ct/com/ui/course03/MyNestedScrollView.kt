package ct.com.ui.course03

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.NestedScrollingParent3
import androidx.core.widget.NestedScrollView

class MyNestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    //====================父控件是否准备处理子控件通知的嵌套滑动事件 DOWN事件==========================
    /**
     *              比如:NestedScrollView
     *                    |-LinearLayout
     *                       |-ViewPager
     *                          |-RecyclerView
     *
     * 当在一个子View发生嵌套滚动操作时,父控件可以捕获适合自己的滚动事件
     *
     * 这个方法会在 {@link ViewCompat#startNestedScroll(View, int)}调用时调用
     *
     * @param child 包含目标子View(发生嵌套滑动的View)的直接子View 也就是上面的LinearLayout
     * @param target 发生嵌套滚动的View 也就是上面的RecyclerView
     * @param axes  由{@link ViewCompat#SCROLL_AXIS_HORIZONTAL}, {@link ViewCompat#SCROLL_AXIS_VERTICAL} or both 组成的标记
     * @param type  表示这次滚动事件类型
     *              TYPE_TOUCH: 由用户触发的触摸事件
     *              TYPE_NON_TOUCH：不是由用户触发的事件,通常是Fling
     *
     *
     * @return true 表示该父控件接收这个嵌套滚动的操作
     * */
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        Log.e(
            "NestedScrollView",
            "onStartNestedScroll:child:$child -- target:$target -- axes:$axes -- type:$type"
        )
        return super.onStartNestedScroll(child, target, axes, type)
    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        Log.e("NestedScrollView", "onStartNestedScroll:child:$child -- target:$target")
        return super.onStartNestedScroll(child, target, nestedScrollAxes)
    }


    //========================当嵌套滚动被接收 DOWN事件============================

    /**
     * 对成功获取的嵌套滚动事件作出响应
     *
     * 这个方法会在
     * {@link #onStartNestedScroll(View, View, int, int) onStartNestedScroll} 调用并返回true时调用.
     * */

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        Log.e(
            "NestedScrollView",
            "onStartNestedScroll:child:$child -- target:$target -- axes:$axes -- type:$type"
        )
        super.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int) {
        Log.e("NestedScrollView", "onStartNestedScroll:child:$child -- target:$target ")
        super.onNestedScrollAccepted(child, target, nestedScrollAxes)
    }

    //=============================当嵌套滚动开始前 MOVE事件=============================

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        super.onNestedPreScroll(target, dx, dy, consumed)
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed, type)
    }

    //===========================当开始嵌套滚动 MOVE事件=================================




}

class A : NestedScrollingParent3 {

    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        TODO("Not yet implemented")

    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        Log.e("MyNestedScrollView", "onNestedScrollAccepted:$child -- $target -- $axes $type")
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        TODO("Not yet implemented")
    }


    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNestedScrollAxes(): Int {
        TODO("Not yet implemented")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        TODO("Not yet implemented")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        TODO("Not yet implemented")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        TODO("Not yet implemented")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        TODO("Not yet implemented")
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        TODO("Not yet implemented")
    }

    override fun onStopNestedScroll(target: View) {
        TODO("Not yet implemented")
    }
}