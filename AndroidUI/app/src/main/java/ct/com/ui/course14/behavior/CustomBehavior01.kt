package ct.com.ui.course14.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.coordinatorlayout.widget.CustomCoordinatorLayout
import androidx.core.view.WindowInsetsCompat

/**
 *
 * 泛型:指定可以使用这个Behavior的View的类型
 *     如果 我们指定 泛型是 TextView 那么能够使用这个Behavior的控件就只能是TextView或它的子类
 *
 * CoordinationLayout 可以协调子控件的
 *    1. 处理子控件之间依赖下的交互    --->CustomBehavior01
 *    2. 处理子控件之间的嵌套滑动      --->CustomBehavior02
 *    3. 处理子控件的测量与布局       --->CustomBehavior03
 *    4. 处理子控件的事件拦截与响应    --->CustomBehavior04
 *
 * */
class CustomBehavior01(context: Context?, attrs: AttributeSet?) :
    CustomCoordinatorLayout.Behavior<View>(context, attrs) {

    companion object {
        private const val DEBUG = true
        private const val TAG = "Behavior01"
        private fun log(log: String, printLog: Boolean = true) {
            if (DEBUG && printLog)
                Log.e(TAG, log)
        }
    }

    /**
     * 处理子控件之间依赖下的交互
     *
     * */

    override fun layoutDependsOn(
        parent: CustomCoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        log("layoutDependsOn:$child $dependency")
        return dependency is LinearLayout
    }

    override fun onDependentViewChanged(
        parent: CustomCoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        log("onDependentViewChanged:$child $dependency")
        return super.onDependentViewChanged(parent, child, dependency)
    }

    override fun onApplyWindowInsets(
        coordinatorLayout: CustomCoordinatorLayout,
        child: View,
        insets: WindowInsetsCompat
    ): WindowInsetsCompat {
        log("onApplyWindowInsets:$child")
        return super.onApplyWindowInsets(coordinatorLayout, child, insets)
    }

    /**
     *处理子控件的测量与布局
     * */
    override fun onMeasureChild(
        parent: CustomCoordinatorLayout,
        child: View,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        log("onMeasureChild:$child")
        return super.onMeasureChild(
            parent,
            child,
            parentWidthMeasureSpec,
            widthUsed,
            parentHeightMeasureSpec,
            heightUsed
        )
    }

    override fun onLayoutChild(
        parent: CustomCoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        log("onLayoutChild:$child")
        return super.onLayoutChild(parent, child, layoutDirection)
    }


    override fun onDependentViewRemoved(
        parent: CustomCoordinatorLayout,
        child: View,
        dependency: View
    ) {
        super.onDependentViewRemoved(parent, child, dependency)
    }


    /**
     * 处理子控件之间的嵌套滑动
     * */

    override fun onStartNestedScroll(
        coordinatorLayout: CustomCoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return super.onStartNestedScroll(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CustomCoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onNestedScrollAccepted(
        coordinatorLayout: CustomCoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ) {
        super.onNestedScrollAccepted(
            coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type
        )
    }


    /**
     * 处理子控件的事件拦截与响应
     * */
    override fun onInterceptTouchEvent(
        parent: CustomCoordinatorLayout,
        child: View,
        ev: MotionEvent
    ): Boolean {
        return super.onInterceptTouchEvent(parent, child, ev)
    }
}