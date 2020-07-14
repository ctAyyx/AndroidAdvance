package ct.com.ui.course04.demo02

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs

class MySwipeRefreshLayout
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SwipeRefreshLayout(context, attrs) {


    /**
     * 在这里 默认拦截了所有事件
     * */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN)
            return super.onInterceptTouchEvent(ev)

        //这里拦截除了DOWN事件的其它所有事件
        //出现的冲突 : 可以下拉刷新 但是ViewPager不能左右滑动
        return true
    }
}

class MyViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewPager(context, attrs) {


    private var downX: Float = 0f
    private var downY: Float = 0f


    /**
     *
     * 针对 父控件拦截了除了DOWN事件的其它所有事件
     *
     * 解决的冲突 : 可以下拉刷新 但是ViewPager不能左右滑动
     * */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("TAG", "ViewPager Down事件")
                downX = ev.x
                downY = ev.y
                parent

                    .requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                //获取移动的方向
                val moveX = ev.x
                val moveY = ev.y
                return if (abs(downX - moveX) > abs(downY - moveY)) {
                    //左右
                    super.onTouchEvent(ev)
                } else {
                    //上下
                    parent
                        .requestDisallowInterceptTouchEvent(false)
                    false
                }
            }
        }

        return super.onTouchEvent(ev)
    }

    /**
     * 这里直接返回true
     * 避免SwipeRefreshLayout走自己重写的逻辑
     * */
    override fun isNestedScrollingEnabled(): Boolean {
        return true
    }


}


//=====================================================================================


class MySwipeRefreshLayout02
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SwipeRefreshLayout(context, attrs) {


    /**
     * 在这里 默认拦截了所有事件
     * */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN)
            return super.onInterceptTouchEvent(ev)


        //这里拦截事件交由ViewGroup处理
        //出现的冲突： 可有左右滑动 不能下拉刷新
        return (ev!!.isFromSource(InputDevice.SOURCE_MOUSE)
                && ev.isButtonPressed(MotionEvent.BUTTON_PRIMARY))
    }
}


class MyViewPager02 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewPager(context, attrs) {


    private var downX: Float = 0f
    private var downY: Float = 0f

    private var isToRefresh = false


    /**
     *
     * 针对 父控件拦截了除了DOWN事件的其它所有事件
     *
     * 解决的冲突 : 可以下拉刷新 但是ViewPager不能左右滑动
     * */
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {

                downX = ev.x
                downY = ev.y

                isToRefresh = false
                return super.onTouchEvent(ev)
            }

            MotionEvent.ACTION_MOVE -> {
                //获取移动的方向
                val moveX = ev.x
                val moveY = ev.y
                return if (abs(downX - moveX) > abs(downY - moveY)) {
                    //左右
                    super.onTouchEvent(ev)
                } else {
                    //上下

                    val method = View::class.java.getDeclaredMethod(
                        "onTouchEvent",
                        MotionEvent::class.java
                    )
                    method.isAccessible = true
                    method.invoke(parent, ev)
                    isToRefresh = true
                    false
                }
            }

        }

        if (isToRefresh) {
            val method = View::class.java.getDeclaredMethod(
                "onTouchEvent",
                MotionEvent::class.java
            )
            method.isAccessible = true
            method.invoke(parent, ev)

        }

        return super.onTouchEvent(ev)
    }


}