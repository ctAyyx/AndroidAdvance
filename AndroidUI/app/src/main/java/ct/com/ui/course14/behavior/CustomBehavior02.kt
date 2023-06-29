package ct.com.ui.course14.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CustomCoordinatorLayout

/**
 *
 * 泛型:指定可以使用这个Behavior的View的类型
 *     如果 我们指定 泛型是 TextView 那么能够使用这个Behavior的控件就只能是TextView或它的子类
 *
 * */
class CustomBehavior02(context: Context?, attrs: AttributeSet?) :
    CustomCoordinatorLayout.Behavior<View>(context, attrs) {


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
     * 处理子控件的测量与布局
     * */
    override fun onMeasureChild(
        parent: CustomCoordinatorLayout,
        child: View,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int
    ): Boolean {
        return super.onMeasureChild(
            parent,
            child,
            parentWidthMeasureSpec,
            widthUsed,
            parentHeightMeasureSpec,
            heightUsed
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