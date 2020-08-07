package ct.com.ui.course06

import android.graphics.*
import android.graphics.drawable.Drawable

class FishDrawable : Drawable() {

    private val mPaint: Paint by lazy {
        Paint()
    }


    private val baseR = 10

    //中心点坐标
    private var centerX = 0f
    private var centerY = 0f

    //角度
    private var fishAngle = 0

    private val point = PointF()

    override fun draw(canvas: Canvas) {


    }


    /**
     * 更据弧度
     * */


    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getIntrinsicHeight(): Int {
        return baseR * 13
    }

    override fun getIntrinsicWidth(): Int {
        return baseR * 13
    }
}