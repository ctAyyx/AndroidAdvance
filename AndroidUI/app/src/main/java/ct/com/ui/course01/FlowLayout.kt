package ct.com.ui.course01

import android.content.Context
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY
import kotlin.math.max

/**
 * TIME : 2020/7/4 0004
 * AUTHOR : CT
 * DESC : 流式布局
 *
 * 需求1 实现流式布局
 * 需求2 加入子控件的外边距属性
 * TODO 需求3 实现Gravity属性
 *
 */
class FlowLayout @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ViewGroup(context, attrs, defStyleAttr) {


    //每个Item在横向上的间隔
    private val intervalWidth = 16

    //每个Item在纵向上的间隔
    private val intervalHeight = 16

    //定义一个集合 保存每个Item的原点
    //private val mPointList = mutableListOf<Point>()

    /**
     * 度量控件自身宽高
     *
     * @param widthMeasureSpec 由父控件传递该控件的宽度建议值 需要使用MeasureSpec解码 高2为表示父控件对该控件的测量模式 低30位表示父控件给定的建议宽度
     *
     * 宽度要求有三种模式
     * AT_MOST     子控件可以任意的增大 直到指定的大小
     * EXACTLY     父控件给子控件确定了精确的值，不管子控件想要多大 都会限制在这个值里面
     * UNSPECIFIED 父控件不对子控件施加任何约束，子空间想要多大就多大
     *
     *
     * if EXACTLY //父控件给定了一个精确的值
     *    case 子控件宽度>0,子控件的宽度模式是 EXACTLY
     *    case 子控件宽带== MATCH_PARENT ,子控件的宽度模式是 EXACTLY
     *    case 子控件宽度== WRAP_CONTENT ,子控件的宽度模式是 AT_MOST
     *
     * if AT_MOST //父控件给定了一个最大值
     *    case 子控件宽度>0,子控件的宽度模式是 EXACTLY
     *    case 子控件宽带== MATCH_PARENT ,子控件的宽度模式是 AT_MOST
     *    case 子控件宽度== WRAP_CONTENT ,子控件的宽度模式是 AT_MOST
     *
     * if UNSPECIFIED //这个一般是系统才会使用
     *
     * @param heightMeasureSpec
     * */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {


        //printMeasureSpec(widthMeasureSpec, heightMeasureSpec)

        // super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        //mPointList.clear()



        //获取父控件给定的测量模式
        val widthModel = MeasureSpec.getMode(widthMeasureSpec)
        val heightModel = MeasureSpec.getMode(heightMeasureSpec)

        //获取父控件给定的宽高建议值
        val widthOffer = getOfferWidth(widthMeasureSpec)
        val heightOffer = getOfferHeight(heightMeasureSpec)
        //定义最终宽高
        var realWidth = 0 + paddingLeft + paddingRight
        //var realHeight = 0 + paddingTop + paddingBottom
        //这里只初始顶部内边距 为了获取每行的Rect
        var realHeight = 0 + paddingTop

        //定义临时宽高
        var tempWidth = 0
        var tempHeight = 0

        //==1==测量每个子控件的宽高
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == View.GONE)
                continue

            //val point = Point(paddingLeft, paddingTop)
            //测量外边距
            //使用该方法后 就不需要调用以下的方法了
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, 0)

            //这里通过getChildMeasureSpec方法来确定应该给子控件的MeasureSpec
            // spec     该控件的测量模式
            // padding  该控件的内边距 只有减去了内边距的值 才是该控件能给为子控件提供的最大空间
            // childDimension 子控件LayoutParams的宽高值
//            val params = childView.layoutParams as LayoutParams
//            val childWidthMeasureSpec =
//                getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, params.width)
//            val childHeightMeasureSpec =
//                getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, params.height)
//            //开始测量
//            childView.measure(childWidthMeasureSpec, childHeightMeasureSpec)

            //==2==根据测量完成后计算该控件的宽高

            //获取子控件期望占据的空间 宽度+外边距

            val childWidth = getChildViewWidth(childView)
            val childHeight = getChildViewHeight(childView)
            //开始计算该控件
            Log.e(
                "TAG",
                "${paddingLeft}判断是否需要换行:$tempWidth -- $i--${tempWidth + childWidth} -- $tempHeight"
            )
            if (widthOffer < tempWidth + childWidth) {
                //准备换行
                realHeight += tempHeight + intervalHeight
                realWidth = max(realWidth, tempWidth + paddingLeft + paddingRight)

                tempHeight = 0
                tempWidth = 0
                Log.e("TAG", "换行====${realHeight} -- $i")

            }
            tempHeight = max(tempHeight, childHeight)
            tempWidth += childWidth + intervalWidth


        }

        //针对最后一行
        if (tempWidth > 0)
            realWidth = max(realWidth, tempWidth)
        if (tempHeight > 0)
            realHeight += tempHeight
        realHeight += paddingBottom


        //测量 主要是针对 AT_MOST 模式
        Log.e("TAG", "最终计算出来的值:$realWidth -- $realHeight")
//        if (realHeight > heightOffer)
//            realHeight = heightOffer
        if (widthModel == MeasureSpec.EXACTLY)
            realWidth = MeasureSpec.getSize(widthMeasureSpec)
        if (heightModel == MeasureSpec.EXACTLY)
            realHeight = MeasureSpec.getSize(heightMeasureSpec)

        setMeasuredDimension(realWidth, realHeight)

    }


    /**
     * 定位子控件的布局位置
     * */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //获取该控件测量后的宽高
        val realWidth = measuredWidth
        val realHeight = measuredHeight
        Log.e("TAG", "OnLayout:$realWidth -- $realHeight")
        //定义临时宽高 表示已经分配的宽高
        var tempWidth = 0 + paddingLeft
        var tempHeight = 0 + paddingTop

        var longHeight = tempHeight
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == View.GONE)
                continue
            //获取子控件的占据的空间
            //获取子控件期望占据的空间 宽度+外边距
            val childWidth = getChildViewWidth(childView)
            val childHeight = getChildViewHeight(childView)
            //优化
            if (realWidth < tempWidth + childWidth) {
                tempWidth = 0 + paddingLeft
                tempHeight = longHeight + intervalHeight
            }
            val right = tempWidth + childWidth
            val bottom = tempHeight + childHeight
            childView.layout(tempWidth, tempHeight, right, bottom)
            tempWidth += childWidth + intervalWidth
            longHeight = max(longHeight, bottom)

        }

    }


    /**
     * 获取该控件还能提供的绘制空间
     * */
    private fun getOfferWidth(widthMeasureSpec: Int) =
        MeasureSpec.getSize(widthMeasureSpec) - paddingStart - paddingEnd

    private fun getOfferHeight(heightMeasureSpec: Int) =
        MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom


    /**
     * 获取子控件占用的空间
     * 应该是 子控件的宽度+子控件外边距
     *
     * 现在这里只考虑子控件宽度
     *
     * TODO 加上子控件的外边距
     * */
    private fun getChildViewWidth(child: View): Int {
        val params = child.layoutParams as LayoutParams

        return child.measuredWidth + params.marginStart + params.marginEnd
    }

    private fun getChildViewHeight(child: View): Int {
        val params = child.layoutParams as LayoutParams
        return child.measuredHeight + params.topMargin + params.bottomMargin
    }


    /**
     * 打印 父控件根据该控件的LayoutPrams给定的测量模式
     * */
    private fun printMeasureSpec(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthModel = when (MeasureSpec.getMode(widthMeasureSpec)) {
            MeasureSpec.EXACTLY -> "EXACTLY"
            MeasureSpec.AT_MOST -> "AT_MOST"
            MeasureSpec.UNSPECIFIED -> "UNSPECIFIED"
            else -> "你TM逗我呢?"
        }

        val heightModel = when (MeasureSpec.getMode(heightMeasureSpec)) {
            MeasureSpec.EXACTLY -> "EXACTLY"
            MeasureSpec.AT_MOST -> "AT_MOST"
            MeasureSpec.UNSPECIFIED -> "UNSPECIFIED"
            else -> "你TM逗我呢?"
        }

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        Log.e(
            "TAG",
            "父控件根据该控件的LayoutPrams给定的测量模式:${widthModel} --- $width --高度模式${heightModel} -- $height"
        )
    }


    override fun generateLayoutParams(attrs: AttributeSet?): ViewGroup.LayoutParams {
        //return super.generateLayoutParams(attrs)
        return LayoutParams(context, attrs)
    }


    class LayoutParams : MarginLayoutParams {

        private var gravity: Int = UNSPECIFIED_GRAVITY

        constructor(c: Context?, attrs: AttributeSet?) : super(c, attrs)

        constructor(width: Int, height: Int, gravity: Int = UNSPECIFIED_GRAVITY) : super(
            width,
            height
        ) {
            this.gravity = gravity
        }

        constructor(params: MarginLayoutParams) : super(params)
        constructor(params: ViewGroup.LayoutParams) : super(params)
        constructor(params: LayoutParams) : super(params) {
            this.gravity = params.gravity
        }


    }
}

