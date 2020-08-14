package ct.com.ui.course08

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView

class MyFragmeLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val fishDrawable by lazy { FishDrawable02() }
    private val imgFish by lazy {
        val imgFish = ImageView(context)
        val layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        imgFish.layoutParams = layoutParams
        imgFish.setImageDrawable(fishDrawable)

        imgFish
    }


    private var downX: Float = 0f
    private var downY: Float = 0f

    private val mPaint by lazy { Paint().apply { style = Paint.Style.STROKE } }
    private val mPath by lazy { Path() }

    init {
        addView(imgFish)
        setWillNotDraw(false)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_DOWN) {
            downX = event.x
            downY = event.y
            invalidate()
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawPath(canvas)

    }

    private fun drawPath(canvas: Canvas) {
        mPath.reset()
        //鱼的重心
        val mFishCenterPoint = fishDrawable.middlePoint
        //鱼的头
        val mFishHeadPoint = fishDrawable.headPoint ?: return
        mPath.moveTo(mFishCenterPoint.x, mFishCenterPoint.y)
        mPath.quadTo(mFishHeadPoint.x, mFishHeadPoint.y, downX, downY)

        canvas.drawPath(mPath, mPaint)
    }


    /**
     * 向量的夹角
     * AOB的夹角
     * 向量OA(a1,a2) 向量OB(b1,b2)
     * cos = OA*OB/|OA|*|OB|
     *
     * */

    private fun aa() {

    }


}