package ct.com.ui.course05.basics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.math.min

/**
 *  混合模式
 */
class PorterDuffView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint by lazy { Paint() }

    private var centerX = 0
    private var centerY = 0

    private var radiusX = 0f
    private var radiusY = 0f
    private var radius = 0f

    private var rect = RectF()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        centerX = measuredWidth / 2
        centerY = measuredHeight / 2
        radiusX = centerX + centerX / 4f
        radiusY = centerY - centerY / 4f
        radius = min(centerX / 2f, centerY / 2f)
        rect.set(radiusX - 2 * radius, radiusY, radiusX, radiusY + radius * 2)

        // 这里需要保存画布 不然会出现黑色
        val count =
            canvas.saveLayer(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), mPaint)

        drawDestination(canvas)

        //使用混合模式

        // 将source添加到destination并使结果饱和
        //val mode = PorterDuff.Mode.ADD

        //source被丢弃，留下完整的destination
        //val mode = PorterDuff.Mode.DST

        //
        val mode = PorterDuff.Mode.DST_OUT
        mPaint.xfermode = PorterDuffXfermode(mode)


        drawSource(canvas)
        mPaint.xfermode = null

        canvas.restoreToCount(count)
    }

    private fun drawDestination(canvas: Canvas) {
        mPaint.color = Color.RED
        canvas.drawCircle(radiusX, radiusY, radius, mPaint)
    }

    private fun drawSource(canvas: Canvas) {
        mPaint.color = Color.BLUE
        canvas.drawRect(rect, mPaint)
    }


}