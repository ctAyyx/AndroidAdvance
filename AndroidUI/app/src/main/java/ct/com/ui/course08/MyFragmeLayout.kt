package ct.com.ui.course08

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import android.widget.ImageView
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.sqrt

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
            drawPath()
            // invalidate()
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        invalidate()
    }

    /**
     * 绘制 鱼游动的路径
     * */
    private fun drawPath() {

        //鱼的重心的相对坐标
        val mFishRelativeCenterPoint = fishDrawable.middlePoint

        //鱼的重心的绝对坐标
        val mFishCenterPoint =
            PointF(imgFish.x + mFishRelativeCenterPoint.x, imgFish.y + mFishRelativeCenterPoint.y)


        //鱼的头中心相对坐标
        val mFishHeadRelativePoint = fishDrawable.headPoint
        val mFishHeadPoint =
            PointF(imgFish.x + mFishHeadRelativePoint.x, imgFish.y + mFishHeadRelativePoint.y)

        //按下点的坐标
        val downPoint = PointF(downX, downY)


        //获取 第二个控制点的角度
        val secondPointAngle = cosAOB(mFishHeadPoint, mFishCenterPoint, downPoint) / 2f

        //获取AO 与X轴的夹角
        val aoAngle = cosAOB(
            mFishHeadPoint, mFishCenterPoint,
            PointF(mFishCenterPoint.x + 1, mFishCenterPoint.y)
        )
        //获取 BO与X轴的夹角
        val boAngle = secondPointAngle + aoAngle

        //获取控制点二的坐标
        val secondPoint = fishDrawable.calculatePoint(
            mFishCenterPoint,
            fishDrawable.HEAD_RADIUS * 1.6f,
            boAngle.toDouble()
        )

        mPath.reset()
        mPath.moveTo(
            mFishCenterPoint.x - mFishRelativeCenterPoint.x,
            mFishCenterPoint.y - mFishRelativeCenterPoint.y
        )
        mPath.cubicTo(
            mFishHeadPoint.x,
            mFishHeadPoint.y,
            secondPoint.x,
            secondPoint.y,
            downX - mFishRelativeCenterPoint.x,
            downY - mFishRelativeCenterPoint.y
        )
        //通过动画来改变鱼的坐标
        val animator = ObjectAnimator.ofFloat(imgFish, "x", "y", mPath)
        animator.duration = 4000L
        animator.start()

        val pathMeasure = PathMeasure(mPath, false)
        val tan = FloatArray(2)
        animator.addUpdateListener {
            val fraction = it.animatedFraction
            pathMeasure.getPosTan(pathMeasure.length * fraction, null, tan)

            val angle = Math.toDegrees(atan2(-tan[1].toDouble(), tan[0].toDouble()))
            fishDrawable.fishMainAngle = angle
        }

    }


    /**
     * 向量的夹角
     * AOB的夹角
     * 向量OA(a1,a2) 向量OB(b1,b2)
     * OA*OB =a1*b1+a2*b2
     * cos = OA*OB/|OA|*|OB|
     *
     * */

    private fun cosAOB(pointA: PointF, pointO: PointF, pointB: PointF): Float {
        // 获取OA * OB的值
        val AOB =
            (pointO.x - pointA.x) * (pointO.x - pointB.x) + (pointO.y - pointA.y) * (pointO.y - pointB.y)

        //获取 |OA|*|OB|
        val OA =
            sqrt((pointO.x - pointA.x) * (pointO.x - pointA.x) + (pointO.y - pointA.y) * (pointO.y - pointA.y))
        val OB =
            sqrt((pointO.x - pointB.x) * (pointO.x - pointB.x) + (pointO.y - pointB.y) * (pointO.y - pointB.y))

        //获取AOB夹角
        val cosAOB = AOB / (OA * OB)

        //获取AOB角度 0~180
        val angleAOB = Math.toDegrees(acos(cosAOB.toDouble()))
        Log.e("TAG", "$angleAOB----${acos(cosAOB.toDouble())}")

        //计算 AB OB的tan值
        val tanAB = (pointA.y - pointB.y) / (pointA.x - pointB.x)
        val tanOB = (pointO.y - pointB.y) / (pointO.x - pointB.x)

        //获取 tanAB 与 tanOB 的差
        //用来确定点击的坐标是在鱼的那边
        val direction = tanAB - tanOB


        //更据tan函数
        // 如果在AO的右边 direction <0
        // AOB的角度就等于 angleAOB
        // 如果在Ao的左边 direction >0
        // AOB的角度就等于 -angleAOB
        // 如果在AO线上   direction =0
        //看向量OA*OB = |OA|*|OB|*cos
        //如果OA*OB >=0 则 cos >=0 则按下的坐标的鱼的上方
        //否则在鱼的下放

        return if (direction == 0f) {
            if (AOB >= 0)
                0f
            else
                180f
        } else {
            if (direction > 0)
                -angleAOB.toFloat()
            else
                angleAOB.toFloat()

        }


    }


}