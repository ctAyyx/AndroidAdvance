package ct.com.ui.course15

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import ct.com.ui.R

/**
 *
 * 图片查看控件
 *
 * 实现: 1.单击放大 缩小
 *      2.图片放大后 拖动
 *      3.双指缩放
 *
 * */
class PhotoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }

    private val bitmap by lazy { BitmapFactory.decodeResource(resources, R.drawable.photo01) }


    override fun onDraw(canvas: Canvas) {

        canvas.drawBitmap(
            bitmap,
            width / 2f - bitmap.width / 2f,
            height / 2f - bitmap.height / 2f,
            mPaint
        )

    }


    /**
     * 将图片适应屏幕放大
     * */
    private fun drawMatch() {



    }


}