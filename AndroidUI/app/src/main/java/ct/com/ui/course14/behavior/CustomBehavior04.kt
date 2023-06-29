package ct.com.ui.course14.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import androidx.coordinatorlayout.widget.CustomCoordinatorLayout

/**
 *
 * 泛型:指定可以使用这个Behavior的View的类型
 *     如果 我们指定 泛型是 TextView 那么能够使用这个Behavior的控件就只能是TextView或它的子类
 *
 * */
class CustomBehavior04(context: Context?, attrs: AttributeSet?) :
    CustomCoordinatorLayout.Behavior<TextView>(context, attrs) {

    /**
     * 处理子控件的事件拦截与响应
     * */
    override fun onInterceptTouchEvent(
        parent: CustomCoordinatorLayout,
        child: TextView,
        ev: MotionEvent
    ): Boolean {
        Log.e("TAG", "onInterceptTouchEvent ====> ${child::class.java.simpleName}")
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    override fun onTouchEvent(
        parent: CustomCoordinatorLayout,
        child: TextView,
        ev: MotionEvent
    ): Boolean {
        Log.e("TAG", "onTouchEvent ====> ${child::class.java.simpleName}")
        return super.onTouchEvent(parent, child, ev)
    }
}