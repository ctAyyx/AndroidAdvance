package ct.com.ui.course03

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView


/**
 *  NestedScrollingParent 嵌套滑动的父控件需要实现的接口
 *
 *  DOWN 事件
 *  1. onStartNestedScroll      当子控件接收到DOWN事件的时候 会回调这个方法 判断父控件是否需要滑动
 *  2. onNestedScrollAccepted   如果父控件接收了嵌套滑动 则会回调这个方法
 *  MOVE 事件
 *  3. onNestedPreScroll        当子控件准备开始滑动的时候 会调用这个方法 看父控件是否会消费这个滚动事件
 *  4. onNestedScroll           当子控件在本次滚动完成后 会回调这个方法 看父控件是否会消费这个滚动事件
 *  UP 事件
 *  5. onNestedPreFling         在子控件消费这个Flying事件之前 看父控件是否消费
 *  6. onNestedFling            在子控件判断是否消费这个Flying事件之后 看父控件是否继续消费
 *  7. onStopNestedScroll       停止滚动
 *
 * */


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
            "onStartNestedScroll:child:${child::class.simpleName} -- target:${target::class.simpleName} -- axes:$axes -- type:$type"
        )

        return super.onStartNestedScroll(child, target, axes, type)
    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        Log.e(
            "NestedScrollView",
            "onStartNestedScroll(1):child:${child::class.simpleName} -- target:${target::class.simpleName} -- $nestedScrollAxes"
        )
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
            "onNestedScrollAccepted:child:${child::class.simpleName} -- target:${target::class.simpleName} -- axes:$axes -- type:$type"
        )
        super.onNestedScrollAccepted(child, target, axes, type)
    }

    override fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int) {
        Log.e(
            "NestedScrollView",
            "onNestedScrollAccepted:child:${child::class.simpleName} -- target:${target::class.simpleName} "
        )
        super.onNestedScrollAccepted(child, target, nestedScrollAxes)
    }

    //=============================当嵌套滚动开始前 MOVE事件=============================

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {

        super.onNestedPreScroll(target, dx, dy, consumed)
        Log.e(
            "NestedScrollView",
            "onNestedPreScroll: target:${target::class.simpleName} --- dx:$dx --- dy:$dy -- ${consumed.contentToString()}"
        )
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

        super.onNestedPreScroll(target, dx, dy, consumed, type)
        Log.e(
            "NestedScrollView",
            "onNestedPreScroll: target:${target::class.simpleName} --- dx:$dx --- dy:$dy -- consumed:${consumed.contentToString()} -- type:$type"
        )
    }

    //==================== 当开始嵌套滚动事件 ============================
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {

        super.onNestedScroll(
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )
        Log.e(
            "NestedScrollView",
            "onNestedScroll(3): target:${target::class.simpleName} --- dxConsumed:$dxConsumed --- dyConsumed:$dyConsumed -- dxUnconsumed:$dxUnconsumed -- dyUnconsumed:$dyUnconsumed-- ${consumed.contentToString()}"
        )
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {

        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
        Log.e(
            "NestedScrollView",
            "onNestedScroll: target:${target::class.simpleName} --- dxConsumed:$dxConsumed --- dyConsumed:$dyConsumed -- dxUnconsumed:$dxUnconsumed -- dyUnconsumed:$dyUnconsumed"
        )
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {

        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        Log.e(
            "NestedScrollView",
            "onNestedScroll: target:${target::class.simpleName} --- dxConsumed:$dxConsumed --- dyConsumed:$dyConsumed -- dxUnconsumed:$dxUnconsumed -- dyUnconsumed:$dyUnconsumed"
        )
    }

//    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
//        Log.e(
//            "NestedScrollView",
//            "onNestedPreFling: target:${target::class.simpleName} --- velocityX:$velocityX --- velocityY:$velocityY "
//        )
//        return super.onNestedPreFling(target, velocityX, velocityY)
//    }
//
//    /**
//     * @param consumed 子控件是否消费了 Flying
//     * */
//    override fun onNestedFling(
//        target: View,
//        velocityX: Float,
//        velocityY: Float,
//        consumed: Boolean
//    ): Boolean {
//        Log.e(
//            "NestedScrollView",
//            "onNestedFling: target:${target::class.simpleName} --- velocityX:$velocityX --- velocityY:$velocityY -- consumed:$consumed"
//        )
//        return super.onNestedFling(target, velocityX, velocityY, consumed)
//    }


    //========================停止嵌套滚动 ======================

//    override fun onStopNestedScroll(target: View) {
//        Log.e(
//            "NestedScrollView",
//            "onStopNestedScroll: target:${target::class.simpleName} "
//        )
//        super.onStopNestedScroll(target)
//    }
//
//    override fun onStopNestedScroll(target: View, type: Int) {
//        Log.e(
//            "NestedScrollView",
//            "onStopNestedScroll: target:${target::class.simpleName} --- type:$type"
//        )
//        super.onStopNestedScroll(target, type)
//    }

    internal class Log {
        companion object {
            fun e(tag: String, msg: String) {
                android.util.Log.e(tag, msg)
            }
        }
    }
}

