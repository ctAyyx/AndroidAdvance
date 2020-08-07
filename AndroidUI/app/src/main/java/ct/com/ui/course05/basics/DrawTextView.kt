package ct.com.ui.course05.basics

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*

/**
 * 对文字的绘制
 *
 * 文字绘制的几个重要线
 *
 * baseline: 文字在Y轴上绘制时 的基准线
 *
 *
 * */
class DrawTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var centerX = 0f
    private var centerY = 0f

    private val radius = 120f
    private val mPaint by lazy {
        Paint()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = width / 2f
        centerY = height / 2f

        //绘制文字的5条重要的线
        //drawTextLine(canvas)

        //绘制文字在Path上
        //drawTextOnPath(canvas)

        //Paint对文字绘制的辅助设置
        //drawTextWithPaint(canvas)

        //测量文字大小
        //measureText(canvas)

        //文本居中
        drawTextCenter(canvas)

    }

    /**
     * 绘制文字 需要注意的几条线
     * */
    private fun drawTextLine(canvas: Canvas) {
        mPaint.isAntiAlias = true
        mPaint.textSize = 140f
        val metrics = mPaint.fontMetrics
        //文字在基线之上的推荐高度
        val ascent = metrics.ascent
        //文字在基线之下推荐的高度
        val descent = metrics.descent

        //文字在基线之上的最大高度
        val top = metrics.top
        //文字在基线之下的最大高度
        val bottom = metrics.bottom
        //文本行之间的建议间距
        val leading = metrics.leading
        mPaint.color = Color.BLACK
        mPaint.textAlign = Paint.Align.CENTER
        canvas.drawText("文字Hello", centerX, centerY, mPaint)

        //绘制基线
        mPaint.color = Color.RED
        val baseLine = centerY
        canvas.drawLine(0f, baseLine, width.toFloat(), baseLine, mPaint)

        //绘制top
        mPaint.color = Color.BLUE
        val lineTop = baseLine + top
        canvas.drawLine(0f, lineTop, width.toFloat(), lineTop, mPaint)

        //绘制bottom
        mPaint.color = Color.parseColor("#9900CC")
        val lineBottom = baseLine + bottom
        canvas.drawLine(0f, lineBottom, width.toFloat(), lineBottom, mPaint)

        //绘制ascent
        mPaint.color = Color.parseColor("#EE7942")
        val lineAscent = baseLine + ascent
        canvas.drawLine(0f, lineAscent, width.toFloat(), lineAscent, mPaint)

        //绘制descent
        mPaint.color = Color.parseColor("#8B8B00")
        val lineDescent = baseLine + descent
        canvas.drawLine(0f, lineDescent, width.toFloat(), lineDescent, mPaint)

    }


    private fun drawTextOnPath(canvas: Canvas) {
        val patch = Path()
        patch.addArc(
            centerX - radius,
            centerY - radius,
            centerX + radius,
            centerY + radius,
            180f,
            180f
        )
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 1.5f
        mPaint.isAntiAlias = true
        canvas.drawPath(patch, mPaint)

        mPaint.style = Paint.Style.FILL
        mPaint.textSize = 32f

        //hOffset 横向偏移
        //vOffset 纵向偏移
        canvas.drawTextOnPath("绘制文字在Path上", patch, 0f, 0f, mPaint)
    }

    /**
     * Paint 对文字绘制的辅助设置
     * */
    private fun drawTextWithPaint(canvas: Canvas) {

        //设置字体大小
        mPaint.textSize = 36f

        //设置文字对齐方式
        mPaint.textAlign = Paint.Align.CENTER

        //设置字体
        mPaint.typeface = Typeface.SANS_SERIF

        //设置字体是否使用伪粗体
        //之所以叫伪粗体（ fake bold ），因为它并不是通过选用更高 weight 的字体让文字变粗，而是通过程序在运行时把文字给「描粗」了。
        mPaint.isFakeBoldText = true

        //是否添加删除线
        mPaint.isStrikeThruText = true

        //是否添加下划线
        mPaint.isUnderlineText = true

        //设置文字的错切角度
        mPaint.textSkewX = -0.25f

        //设置文字横向缩放
        mPaint.textScaleX = 1.2f

        //设置字符间的间距 默认为0
        mPaint.letterSpacing = 0.05f

        //设置文字的区域
        mPaint.textLocale = Locale.JAPAN

        //是否启用字体微调
        mPaint.hinting = Paint.HINTING_ON


        canvas.drawText("Paint对文字绘制的辅助设置", centerX, centerY, mPaint)
    }


    /**
     * 测量文字的大小
     * */
    private fun measureText(canvas: Canvas) {

        mPaint.textSize = 80f
        mPaint.textAlign = Paint.Align.CENTER
        //获取文字换行推荐的行距 baseline之间的距离
        val fontSpacing = mPaint.fontSpacing

        //获取文字显示的范围
        //获取的是针对基线的坐标 这里的基线是0
        val rect = Rect()
        mPaint.getTextBounds("测量文字大小", 0, 6, rect)
        Log.e("TAG", "获取的范围:${rect}")

        //获取文字占用的宽度
        val width = mPaint.measureText("测量文字大小")
        Log.e("TAG", "文字暂用的宽度:$width")

        //measureForwards true表示从第一个往后测量 false表示从最后一个往前测量
        //measuredWidth 返回测量的实际宽度
        //返回值是测量的总数
        val floatArray = FloatArray(1)

        val measureCount = mPaint.breakText("测量文字大小", true, 400f, floatArray)
        Log.e("TAG", "测量总数:$measureCount -- 测量的实际宽度${floatArray[0]}")

        canvas.drawText("测量文字大小", centerX, centerY, mPaint)
        canvas.drawText("行间距", centerX, centerY + fontSpacing, mPaint)

        mPaint.style = Paint.Style.STROKE
        val left = centerX - rect.width() / 2
        val top = centerY + rect.top
        val right = centerX + rect.width() / 2
        val bottom = centerY + rect.bottom

        canvas.drawRect(left, top, right, bottom, mPaint)


    }


    /**
     * 将文字居中绘制
     *
     * 如何将文字居中?
     *
     * 在绘制文字时 传入的Y分别是基线的坐标
     * 那么文字如果要居中 则将文字垂直方向的中心点移动到基线的位置就可以了
     * 也就是基线移动 基线到中心点的距离就可以了
     *  //如何获取中心点坐标
     *  (descent + ascent)/2 负值
     *  //现在的基线是CenterY
     *  //移动基线
     *  CenterY-中心点
     * */
    private fun drawTextCenter(canvas: Canvas) {
mPaint.reset()
        mPaint.textSize = 80f
        //横线居中 直接使用Align 或者算出宽度
        mPaint.textAlign = Paint.Align.CENTER


        val metrics = mPaint.fontMetrics
        val descent = metrics.descent
        val ascent = metrics.ascent

        val baseline = centerY - (descent + ascent) / 2

        canvas.drawText("文本居中", centerX, baseline, mPaint)

        mPaint.color= Color.RED
        mPaint.strokeWidth = 1.5f
        canvas.drawLine(centerX,0f,centerX,height.toFloat(),mPaint)
        canvas.drawLine(0f,centerY,width.toFloat(),centerY,mPaint)


    }

}