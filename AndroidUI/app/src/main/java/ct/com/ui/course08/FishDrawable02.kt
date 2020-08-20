package ct.com.ui.course08

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator
import kotlin.math.cos
import kotlin.math.sin

/**
 * TIME : 2020/8/10 0010
 * AUTHOR : CT
 * DESC :  这里使用 Drawable来绘制锦鲤鱼
 *
 * 1.确定整个Drawable的大小
 * 2.绘制锦鲤
 * 3.添加动画
 *
 */
class FishDrawable02 : Drawable() {

    private val OTHER_ALPHA = 110


    //鱼的重心 及Drawable的中心
    val middlePoint by lazy {
        PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS)
    }

    //鱼起始角度
    var fishMainAngle = 90.0

    //鱼头部大小
    val HEAD_RADIUS = 25f

    //鱼身的长度
    private val BODY_LENGTH = HEAD_RADIUS * 3.2f

    //鱼鳍起始点到鱼头圆心的距离
    private val FINS_START_LENGTH = HEAD_RADIUS * 0.9f

    //鱼鳍的长度
    private val FINS_LENGTH = HEAD_RADIUS * 1.3f

    //鱼身体底部圆的半径
    private val BIG_RADIUS = HEAD_RADIUS * 0.7f

    //鱼尾起始圆的半径
    private val MIDDLE_RADIUS = BIG_RADIUS * 0.6f

    //鱼尾底部圆的半径
    private val SMALL_RADIUS = MIDDLE_RADIUS * 0.4f

    //鱼尾 第一节的距离
    private val FISHTAIL_BIG_LENGTH = BIG_RADIUS + MIDDLE_RADIUS

    private val BIG_TRIANGLE_LENGTH = MIDDLE_RADIUS * 2.1f
    private val SMALL_TRIANGLE_LENGTH = BIG_TRIANGLE_LENGTH * 0.9f

    private val FISHTAIL_SMALL_LENGTH =
        (4.19 * HEAD_RADIUS - BODY_LENGTH / 2 - FISHTAIL_BIG_LENGTH - SMALL_RADIUS).toFloat()


    private val mPath by lazy { Path() }
    private val mPaint by lazy {
        Paint().apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            isDither = true
            setARGB(OTHER_ALPHA, 244, 92, 71)
        }
    }

    private var offsetValue = 0.0

    var rate = 1.0f

    init {
        startAnim()
    }


    lateinit var headPoint: PointF


    override fun draw(canvas: Canvas) {

        //鱼主要朝向
        val fishAngle = fishMainAngle + sin(Math.toRadians(1.2 * offsetValue)) * 10f

        //获取鱼头的圆心坐标
        headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle)

        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint)

        //获取 鱼鳍起始点的坐标
        //右鱼鳍起始点坐标

        val rightFinsStartPoint = calculatePoint(headPoint, FINS_START_LENGTH, fishAngle - 110.0)
        drawFins(canvas, rightFinsStartPoint, fishAngle, true)

        //左鱼鳍起始点坐标
        val leftFinsStartPoint = calculatePoint(headPoint, FINS_START_LENGTH, fishAngle + 110.0)
        drawFins(canvas, leftFinsStartPoint, fishAngle, false)


        //获取鱼身体底部中心坐标
        val bodyBottomCenterPoint = calculatePoint(headPoint, BODY_LENGTH, fishAngle - 180)


        var offsetFishAngle = fishAngle + cos(Math.toRadians(1.5 * offsetValue * rate)) * 15f
        //获取鱼尾第二个圆的圆心
        val middlePoint =
            calculatePoint(bodyBottomCenterPoint, FISHTAIL_BIG_LENGTH, offsetFishAngle - 180)


        //绘制第一个节肢
        drawFishtail(
            canvas,
            bodyBottomCenterPoint,
            middlePoint,
            BIG_RADIUS,
            MIDDLE_RADIUS,
            FISHTAIL_BIG_LENGTH,
            offsetFishAngle,
            true
        )

        offsetFishAngle = fishAngle + sin(Math.toRadians(1.5 * offsetValue * rate)) * 30f
        //获取鱼尾底部圆的圆心
        val smallPoint = calculatePoint(middlePoint, FISHTAIL_SMALL_LENGTH, offsetFishAngle - 180)
        //绘制第二个节肢
        drawFishtail(
            canvas,
            middlePoint,
            smallPoint,
            MIDDLE_RADIUS,
            SMALL_RADIUS,
            FISHTAIL_SMALL_LENGTH,
            offsetFishAngle,
            false
        )


        //绘制三角形
        drawTriangle(canvas, middlePoint, BIG_TRIANGLE_LENGTH, MIDDLE_RADIUS, offsetFishAngle)
        drawTriangle(
            canvas,
            middlePoint,
            SMALL_TRIANGLE_LENGTH,
            MIDDLE_RADIUS * 0.75f,
            offsetFishAngle
        )

        //绘制鱼身体
        drawBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle)


    }

    /**
     * 开启 动画
     * */
    private fun startAnim() {

        val animation = ValueAnimator.ofFloat(0f, 3600f)
            .apply {
                duration = 15 * 1000L
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.RESTART
                interpolator = LinearInterpolator()
            }
        animation.addUpdateListener {
            offsetValue = (it.animatedValue as Float).toDouble()
            invalidateSelf()
        }

        animation.start()

    }


    /**
     * 绘制 锦鲤身体
     * */
    private fun drawBody(
        canvas: Canvas,
        headPoint: PointF,
        bottomPoint: PointF,
        fishAngle: Double
    ) {

        val quadAngle = 170

        val topRightPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90)
        val topLeftPoint = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90)
        val bottomRightPoint = calculatePoint(bottomPoint, BIG_RADIUS, fishAngle - 90)
        val bottomLeftPoint = calculatePoint(bottomPoint, BIG_RADIUS, fishAngle + 90)

        //绘制二次贝赛尔曲线
        val controlRightPoint =
            calculatePoint(topRightPoint, BODY_LENGTH * 0.5f, fishAngle - quadAngle)
        val controlLeftPoint =
            calculatePoint(topLeftPoint, BODY_LENGTH * 0.5f, fishAngle + quadAngle)

        mPath.reset()
        mPath.moveTo(topRightPoint.x, topRightPoint.y)
        mPath.quadTo(
            controlRightPoint.x,
            controlRightPoint.y,
            bottomRightPoint.x,
            bottomRightPoint.y
        )
        mPath.lineTo(bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.quadTo(controlLeftPoint.x, controlLeftPoint.y, topLeftPoint.x, topLeftPoint.y)
        mPath.close()

        canvas.drawPath(mPath, mPaint)

    }


    /**
     * 绘制三角形
     * */
    private fun drawTriangle(
        canvas: Canvas,
        startPoint: PointF,
        length: Float,
        radius: Float,
        fishAngle: Double
    ) {

        //获取三角形 底部中心点坐标
        val triangleCenterPoint = calculatePoint(startPoint, length, fishAngle - 180)


        val rightPoint = calculatePoint(triangleCenterPoint, radius, fishAngle + 90.0)
        val leftPoint = calculatePoint(triangleCenterPoint, radius, fishAngle - 90.0)

        //绘制三角形
        mPath.reset()
        mPath.moveTo(startPoint.x, startPoint.y)
        mPath.lineTo(rightPoint.x, rightPoint.y)
        mPath.lineTo(leftPoint.x, leftPoint.y)
        mPath.close()

        canvas.drawPath(mPath, mPaint)

    }

    /**
     * 绘制节肢
     * */
    private fun drawFishtail(
        canvas: Canvas,
        startPoint: PointF,
        middlePoint: PointF,
        bigRadius: Float,
        smallRadius: Float,
        length: Float,
        fishAngle: Double,
        drawBigCircle: Boolean = true
    ) {


        //获取第一个节肢梯形点的坐标
        val bottomRightPoint = calculatePoint(startPoint, bigRadius, fishAngle - 90)
        val bottomLeftPoint = calculatePoint(startPoint, bigRadius, fishAngle + 90)
        val topRightPoint = calculatePoint(middlePoint, smallRadius, fishAngle - 90)
        val topLeftPoint = calculatePoint(middlePoint, smallRadius, fishAngle + 90)

        //绘制圆
        if (drawBigCircle)
            canvas.drawCircle(startPoint.x, startPoint.y, bigRadius, mPaint)
        canvas.drawCircle(middlePoint.x, middlePoint.y, smallRadius, mPaint)

        //绘制第一节肢梯形
        mPath.reset()
        mPath.moveTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.lineTo(bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(topLeftPoint.x, topLeftPoint.y)
        mPath.lineTo(topRightPoint.x, topRightPoint.y)
        mPath.close()
        canvas.drawPath(mPath, mPaint)

    }

    /**
     * 绘制 鱼鳍 使用二阶贝塞尔曲线
     * */
    private fun drawFins(
        canvas: Canvas,
        startPoint: PointF,
        fishAngle: Double,
        isDrawRight: Boolean = true
    ) {
        val finsAngle =115f+ sin(Math.toRadians(1.1*offsetValue*rate))*10f
        //获取鱼鳍 结束点的坐标
        val endPoint = calculatePoint(startPoint, FINS_LENGTH, fishAngle - 180)

        //获取控制点的坐标
        val controlAngle = if (isDrawRight) fishAngle - finsAngle else fishAngle + finsAngle
        val controlPoint = calculatePoint(startPoint, FINS_LENGTH * 1.8f, controlAngle)

        //绘制
        mPath.reset()
        mPath.moveTo(startPoint.x, startPoint.y)
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y)
        canvas.drawPath(mPath, mPaint)

    }


    /**
     * 根据给定的|点 和 线的长度 获取指定角度点的坐标
     *
     * 这里 我们通过三角函数 来确定点的坐标
     *
     *
     * */
    fun calculatePoint(startPointF: PointF, length: Float, angle: Double): PointF {

        // X 坐标
        val deltaX = (cos(Math.toRadians(angle)) * length).toFloat()
        //Y 坐标
        //因为 Android 坐标系 和数学坐标系Y轴是相反的 所以这里我们要取反
        val deltaY = (sin(Math.toRadians(angle - 180)) * length).toFloat()

        return PointF(startPointF.x + deltaX, startPointF.y + deltaY)

    }

    //========================================

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSPARENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    //==============确定Drawable的大小==============

    //整个Drawable的大小 应该是 鱼重心 到鱼尾的2倍
    //这里默认鱼重心 到鱼尾的距离是头部大小的4.19倍
    override fun getIntrinsicHeight(): Int {
        return (2 * 4.19 * HEAD_RADIUS).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (2 * 4.19 * HEAD_RADIUS).toInt()
    }
}