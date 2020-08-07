package ct.com.ui.course05.basics

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import ct.com.ui.R

/**
 * Canvas 的裁剪 几何变换
 *
 * */
class ClipView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var centerX = 0f
    private var centerY = 0f

    private val radius = 120f

    private val clipRect by lazy {
        RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    }
    private val mPaint by lazy {
        Paint()
    }

    private val bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.skin_bg)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = width / 2f
        centerY = height / 2f
        //裁剪一个矩形区域
        clipReact(canvas)
    }

    /**
     * 裁剪 一个矩形区域
     * */
    private fun clipReact(canvas: Canvas) {

        canvas.save()
        canvas.clipRect(clipRect)
        canvas.drawBitmap(bitmap, 0f, 0f, mPaint)
        canvas.restore()

        canvas.drawColor(Color.RED,PorterDuff.Mode.DST_OVER)

    }
}