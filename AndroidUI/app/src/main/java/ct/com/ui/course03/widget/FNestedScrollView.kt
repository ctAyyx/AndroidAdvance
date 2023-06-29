package ct.com.ui.course03.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import ct.com.ui.course03.FlingHelper

/**
 * 需要实现淘宝的吸顶效果
 *
 * 滚动 就是 画布的移动
 *
 *
 * */
class FNestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    private lateinit var contentView: ViewGroup
    private lateinit var headView: View
    private val mFlingHelper by lazy { FlingHelper(context) }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val childView = getChildAt(0)
        if (childView is ViewGroup) {
            contentView = childView.getChildAt(1) as ViewGroup
            headView = childView.getChildAt(0)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("TAG", "FNestedScrollView====>$measuredHeight")

        //设置contentView的高度为父容器高度
        val params = contentView.layoutParams
        params.height = measuredHeight
        contentView.layoutParams = params

    }


    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        //
        this.target = target
        //当我们的头布局没有隐藏 拦截事件
        val oldScrollY = scrollY
        val hideTop = dy > 0 && oldScrollY < headView.measuredHeight
        if (hideTop) {
            scrollBy(0, dy)
            consumed[1] = dy
        } else
            super.onNestedPreScroll(target, dx, dy, consumed, type)

    }

    /**
     * Flying事件
     * 当在 非 NestedScrollingChild的子控件(HeadView)上快速滑动的 需要在
     * HeadView隐藏后 将事件传递给可以滚动的子控件
     * */

    //可以在 onNestedFling 或则 flying方法中计算 在父控件滚动完成之后剩余的速度
    private var velocityY = 0f
    private var mDy = 0f
    private var target: View? = null

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        this.velocityY = if (velocityY <= 0) 0f else velocityY
        //获取 剩余可以滚动的距离
        mDy = (headView.height - scrollY).toFloat()
        Log.e("TAG", "onNestedFling====>$velocityY")
        if (velocityY > 0f)
            dispatchFlying()
        return super.dispatchNestedFling(velocityX, velocityY, consumed)
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

        super.onNestedScroll(
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed
        )


        //校验为什么NestedScrollView在没有多余空间后 不会继续滚动了
        //myOnNestedScroll(dyUnconsumed, type, consumed)
//        Log.e(
//            "TAG",
//            "onNestedScroll==2===> $scrollY $dyConsumed  $dyUnconsumed ${consumed.contentToString()}"
//        )

    }


    /**
     *
     *  下面是为了验证 为什么NestedScrollView在没有可以滚动的区间后 继续调用scrollBy方法 不会继续滚动了
     *
     *   1.重写了 scrollTo方法
     *   2.通过执行 clamp方法 重新确定了需要滚动的x 和 y
     *   3.如果x y 不等于已经滚动的x y 才执行view的滚动
     * */

    private var helper: NestedScrollingChildHelper? = null
    private fun myOnNestedScroll(dyUnconsumed: Int, type: Int, consumed: IntArray) {
        val oldScrollY = scrollY

        scrollBy(0, dyUnconsumed)
        val myConsumed = scrollY - oldScrollY
        consumed[1] += myConsumed
        val myUnconsumed: Int = dyUnconsumed - myConsumed
        Log.e(
            "TAG",
            "myOnNestedScroll=====> $dyUnconsumed $oldScrollY -- $scrollY -- ${consumed[1]}"
        )
        if (helper == null) {
            val cls = Class.forName("androidx.core.widget.NestedScrollView")
            val field = cls.getDeclaredField("mChildHelper")
            field.isAccessible = true
            helper = field.get(this) as NestedScrollingChildHelper
        }

        helper?.dispatchNestedScroll(0, myConsumed, 0, myUnconsumed, null, type, consumed)
    }

    override fun scrollTo(x: Int, y: Int) {
        //Log.e("scrollTo", "$x $y")
        super.scrollTo(x, y)
    }

    override fun onScrollChanged(mScrollX: Int, mScrollY: Int, oldX: Int, oldY: Int) {
        // Log.e("onScrollChanged", "onScrollChanged ------>$mScrollX $mScrollY ====  $oldX $oldY")
        super.onScrollChanged(mScrollX, mScrollY, oldX, oldY)

        //我们可以在这里在控件滚动的时候 处理Flying事件
//        if (velocityY > 0) {
//            dispatchFlying()
//        }
    }

    private fun dispatchFlying() {

        if (velocityY <= 0)
            return
        //计算距离
        Log.e("TAG", "准备开始分发速度 $velocityY")
        val distance = mFlingHelper.getSplineFlingDistance(velocityY.toInt())
        velocityY = 0F

        //如果可以滚动的距离 大于当前剩余的距离
        //将剩余的距离分发给子View
        //这里我知道 那个是RecyclerView
        val distance2 = distance - mDy
        val velocityY2 = mFlingHelper.getVelocityByDistance(distance2)
        //
        val target = getDispatchView(contentView)
        if (target is RecyclerView) {
            //分发
            Log.e("TAG", "准备开始分发速度 $target")
            target.fling(0, velocityY2)
        }

    }

    private fun getDispatchView(group: ViewGroup): View? {
        val count = group.childCount
        for (i in 0 until count) {
            val view = group.getChildAt(i)

            if (view is RecyclerView && view::class.java.simpleName != "RecyclerViewImpl")
                return view
            if (view is ViewGroup)
                return getDispatchView(view)
        }

        return null
    }
}