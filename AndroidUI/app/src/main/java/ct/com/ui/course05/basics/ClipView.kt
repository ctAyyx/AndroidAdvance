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
    private var startX = 0f
    private var startY = 0f

    private val radius = 120f

    private val clipRect by lazy {
        RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
    }
    private val mPaint by lazy {
        Paint()
    }

    private val bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.mm)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = width / 2f
        centerY = height / 2f
        startX = centerX - bitmap.width / 2f
        startY = centerY - bitmap.height / 2f
        //裁剪一个矩形区域
        //clipReact(canvas)

        //平移
        //translate(canvas)

        //rotate(canvas)

        //scale(canvas)

        use(canvas)


    }

    /**
     * 裁剪 一个矩形区域
     * */
    private fun clipReact(canvas: Canvas) {
        canvas.save()
        canvas.clipRect(clipRect)
        canvas.drawBitmap(bitmap, 0f, 0f, mPaint)
        canvas.restore()
    }

    /**
     * 平移
     * */
    private fun translate(canvas: Canvas) {
        canvas.save()
        canvas.translate(100F, 100F)
        canvas.drawBitmap(bitmap, 0f, 0f, mPaint)
        canvas.restore()
    }

    /**
     * 旋转
     * */
    private fun rotate(canvas: Canvas) {
        canvas.save()
        //将原点作为 旋转的中心点
        canvas.rotate(45f)
        canvas.drawBitmap(bitmap, 0f, 0f, mPaint)
        canvas.restore()
    }


    /**
     * 缩放
     * */
    private fun scale(canvas: Canvas) {
        canvas.save()
        //将原点作为 缩放的中心点
        canvas.scale(3f, 3f, centerX, centerY)
        canvas.drawBitmap(bitmap, 0f, 0f, mPaint)
        canvas.restore()
    }


    //混合 模式
    private fun use(canvas: Canvas) {
        canvas.save()
        //在旋转
        canvas.rotate(30f)
        //先平移
        canvas.translate(150f, 100f)

        canvas.drawBitmap(bitmap, 0f, 0f, mPaint)
        canvas.restore()
    }

}