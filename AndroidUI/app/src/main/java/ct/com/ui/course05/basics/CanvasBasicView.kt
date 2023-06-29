package ct.com.ui.course05.basics

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import ct.com.ui.R


/**
 * 对Canvas 和 Paint 的常见使用
 *
 *1.Canvas 类下的所有 draw- 打头的方法，例如 drawCircle() drawBitmap()。
 *2.Paint 类的几个最常用的方法。具体是：
 *  Paint.setStyle(Style style) 设置绘制模式
 *  Paint.setColor(int color) 设置颜色
 *  Paint.setStrokeWidth(float width) 设置线条宽度
 *  Paint.setTextSize(float textSize) 设置文字大小
 *  Paint.setAntiAlias(boolean aa) 设置抗锯齿开关
 *
 *3.Path的使用
 * 第一类 直接描述路径
 *    路径是有方向的。顺时针 (CW clockwise) 和逆时针 (CCW counter-clockwise) 。
 *    对于普通情况，这个参数填 CW 还是填 CCW 没有影响。
 *    它只是在需要填充图形 (Paint.Style 为 FILL 或 FILL_AND_STROKE) ，并且图形出现自相交时，用于判断填充范围的
 *  第一组 addXxx() -- 添加子图形
 *  第二组 xxxTo()  -- 画线
 *
 * 第二类 辅助的设置或计算
 *  Path.setFillType(Path.FillType ft) 设置填充方式
 *   EVEN_ODD(奇偶原则) 对于平面中的任意一点，向任意方向射出一条射线，这条射线和图形相交的次数（相交才算，相切不算哦）如果是奇数，则这个点被认为在图形内部，是要被涂色的区域；
 *                     如果是偶数，则这个点被认为在图形外部，是不被涂色的区域
 *   WINDING (非零环绕数原则)(默认值) 首先，它需要你图形中的所有线条都是有绘制方向的
 *                                 从平面中的点向任意方向射出一条射线，以 0 为初始值，
 *                                 对于射线和图形的所有交点，遇到每个顺时针的交点（图形从射线的左边向右穿过）把结果加 1，
 *                                 遇到每个逆时针的交点（图形从射线的右边向左穿过）把结果减 1，
 *                                 最终把所有的交点都算上，得到的结果如果不是 0，则认为这个点在图形内部，是要被涂色的区域；
 *                                 如果是 0，则认为这个点在图形外部，是不被涂色的区域
 *   INVERSE_EVEN_ODD 和EVEN_ODD相反
 *   INVERSE_WINDING  和WINDING相反
 *
 * */
class CanvasBasicView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //创建画笔
    private val mPaint: Paint by lazy {
        Paint().apply {
            //开启抗锯齿
            this.isAntiAlias = true
        }
    }

    private var centerX = 0
    private var centerY = 0

    private val radius = 120f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = width / 2
        centerY = height / 2
        //填充画布
        drawColor(canvas)

        //画圆
        //drawCircle(canvas)

        //画矩形
        //drawRect(canvas)

        //画点
        //drawPoint(canvas)

        //画椭圆
        //drawOval(canvas)

        //画线
        //drawLine(canvas)

        //画弧形 扇形
        drawArc(canvas)

        //画 Bitmap
        //drawBitmap(canvas)

        //画 文字
        mPaint.textSize = 40f
        drawText(canvas)

        //画路径
        //drawPath(canvas)
    }

    /**
     * 对Canvas 画布填充指定颜色
     * */
    private fun drawColor(canvas: Canvas) {
        //黑色
        canvas.drawColor(Color.BLACK)
        //白色
        canvas.drawARGB(255, 255, 255, 255)
    }

    /**
     * 绘制一个圆形
     * */
    private fun drawCircle(canvas: Canvas) {

        //给画笔设置一个颜色
        mPaint.color = Color.RED

        //设置画笔的模式
        // mPaint.style = Paint.Style.FILL              // 填充模式
        mPaint.style = Paint.Style.STROKE            // 描边模式
        // mPaint.style = Paint.Style.FILL_AND_STROKE   // 填充和描边模式

        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), mPaint)

    }


    /**
     * 绘制一个矩形
     * */
    private fun drawRect(canvas: Canvas) {
        val left = centerX - radius
        val top = centerY - radius
        val right = centerX + radius
        val bottom = centerY + radius

        mPaint.style = Paint.Style.STROKE


        //canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

        val rx = 10f
        val ry = 120f

        mPaint.color = Color.RED
        canvas.drawRoundRect(
            left.toFloat(),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat(),
            rx,
            ry,
            mPaint
        )
    }


    /**
     * 绘制点
     * */
    private fun drawPoint(canvas: Canvas) {

        mPaint.color = Color.RED

        //点的大小可以通过 设置画布描边的宽度来设置
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 20f

        //默认是矩形 如果想修改点的形状为圆形 可以通过设置画笔线条端点形状来设置
        // ROUND 画出来是圆形的点，SQUARE 或 BUTT 画出来是方形的点
        mPaint.strokeCap = Paint.Cap.ROUND

//        canvas.drawPoint(centerX.toFloat(), centerY.toFloat(), mPaint)

        //绘制一系列的点
        //点需要成对出现
        val pointArray = floatArrayOf(
            120f, 120f,
            160f, 160f,
            120f, 160f,
            160f, 120f
        )
        canvas.drawPoints(pointArray, mPaint)
    }

    /**
     * 绘制椭圆
     * */
    private fun drawOval(canvas: Canvas) {
        val left = centerX - radius
        val top = centerY - radius * 2
        val right = centerX + radius
        val bottom = centerY + radius * 2

        mPaint.style = Paint.Style.STROKE


        canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

        //如果要实心的
        mPaint.style = Paint.Style.FILL
        canvas.drawOval(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)

    }


    /**
     * 画线
     * */
    private fun drawLine(canvas: Canvas) {
        //线的起始坐标
        val startX = centerX - radius
        val startY = centerY - radius * 2

        //线的结束坐标
        val endX = centerX + radius
        val endY = centerY + radius * 2

        mPaint.strokeWidth = 12f //设置线的宽度

        //设置线头的形状
        //mPaint.strokeCap = Paint.Cap.ROUND //圆形
        //mPaint.strokeCap = Paint.Cap.BUTT //平头
        mPaint.strokeCap = Paint.Cap.ROUND //方头 绘制一个矩形 但是绘制出来是一个圆

        canvas.drawLine(startX.toFloat(), startY.toFloat(), endX.toFloat(), endY.toFloat(), mPaint);

        //绘制多条线
        val lines = floatArrayOf(
            20f, 20f, 120f, 20f,
            70f, 20f, 70f, 120f,
            20f, 120f, 120f, 120f,
            150f, 20f, 250f, 20f,
            150f, 20f, 150f, 120f,
            250f, 20f, 250f, 120f,
            150f, 120f, 250f, 120f
        )

        //offset 绘制之前需要跳过数组元素的个数
        //count  跳过之后 需要使用的个数
        canvas.drawLines(lines, 4, 8, mPaint)
    }

    /**
     * 绘制 弧形 或 扇形
     * */
    private fun drawArc(canvas: Canvas) {
        val left = centerX - radius
        val top = centerY - radius
        val right = centerX + radius
        val bottom = centerY + radius

        //可以通过修改画笔的Style 设置是画线 还是填充
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(
            left.toFloat(),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat(),//前面是在定义一个椭圆
            0f,   //开始的角度
            60f, //扫过的角度
            false, //是否连接到圆心
            mPaint
        )

        canvas.drawArc(
            left.toFloat(),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat(),
            90f,
            60f,
            true,
            mPaint
        )
        mPaint.style = Paint.Style.FILL
        canvas.drawArc(
            left.toFloat(),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat(),
            180f,
            60f,
            false,
            mPaint
        )
        canvas.drawArc(
            left.toFloat(),
            top.toFloat(),
            right.toFloat(),
            bottom.toFloat(),
            270f,
            60f,
            true,
            mPaint
        )
    }


    /**
     * 绘制 Bitmap
     * */
    private fun drawBitmap(canvas: Canvas) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.skin_bg)
        canvas.drawBitmap(bitmap, 600f, 600f, mPaint)
    }

    /**
     * 绘制文字
     * */
    private fun drawText(canvas: Canvas) {
        //x X轴上文字开始绘制的起点
        //y Y轴上文字基线的位置
        canvas.drawText("绘制文字", centerX.toFloat(), centerY.toFloat(), mPaint)
    }

    /**
     * 绘制路径
     * */
    private fun drawPath(canvas: Canvas) {

        val path = Path()

        //画线模式
        mPaint.style = Paint.Style.STROKE
        //填充模式
        //mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 6f

        //将起点移动到指定坐标
        path.moveTo(0f, 0f)

        //添加几何图形
//        addCircle(path)
//        addRect(path)
//        addOval(path)
//        addArc(path)

        //添加线
        lineTo(path)
        quadTo(path)
        cubicTo(path)
        arcTo(path)

        //封闭当前图形
        //如果 画笔的Style不是STROKE模式 那默认是会封闭图形的
        //path.close()
        canvas.drawPath(path, mPaint)

    }

    //==============Path第一类方法 直接描述路径========================


    //==============第一组 addXxx() 添加子图形========================

    /**
     * 添加圆形路径
     *
     * 路径是有方向的 CW顺时针 CCW逆时针
     *
     * 如果当前是填充模式
     * */
    private fun addCircle(path: Path) {
        //图形是否填充 要根据画笔的Style
        //添加一个顺时针绘制的圆形路径
        path.addCircle(centerX.toFloat(), centerY.toFloat(), radius, Path.Direction.CW)
        path.addCircle(centerX.toFloat() + 120f, centerY.toFloat(), radius, Path.Direction.CCW)

    }

    /**
     * 添加矩形路径
     *
     * 路径是有方向的 CW 顺时针 CCW 逆时针
     * */
    private fun addRect(path: Path) {
        path.addRect(100f, 100f, 200f, 200f, Path.Direction.CW)
    }

    /**
     * 添加一个椭圆路径
     *
     * 路径是有方向的 CW 顺时针 CWW 逆时针
     * */
    private fun addOval(path: Path) {
        path.addOval(200f, 200f, 400f, 500f, Path.Direction.CW)
    }


    /**
     * 添加一个弧形 路径
     *
     * 路径是有方向的 sweepAngle 大于0 是顺时针 小于0 是逆时针
     * */
    private fun addArc(path: Path) {
        path.addArc(300f, 200f, 500f, 400f, 0f, -180f)
    }


    //====================第二组 xxxTo() 画线====================================
    //当前位置：所谓当前位置，即最后一次调用画 Path 的方法的终点位置。初始值为原点 (0, 0)
    //r开头的就是相对于当前位置为起点
    /**
     * 添加一条直线
     * */
    private fun lineTo(path: Path) {
        //从当前位置画一条到(200,200)坐标的直线
        path.lineTo(200f, 200f)
        //从当前位置画一条相对于当前位置为原点的坐标为(100,200)的直线
        path.rLineTo(100f, 200f)
    }

    /**
     * 添加一条二次贝塞尔曲线
     * */
    private fun quadTo(path: Path) {

        //从当前位置画一条到控制点是(400,400),结束点是(500,100)的二次贝塞尔曲线
        path.quadTo(400f, 400f, 500f, 100f)

        path.rQuadTo(200f, 200f, 100f, 400f)
    }

    /**
     * 添加一条三次贝塞尔曲线
     * */
    private fun cubicTo(path: Path) {

        path.cubicTo(400f, 600f, 500f, 700f, 800f, 500f)

    }

    /**
     *添加一条弧形的曲线
     * */
    private fun arcTo(path: Path) {

        //forceMoveTo 是否将起点移动到弧形开始的位置 false 不移动 那么就会有一条连接当前位置到弧形开始位置的直线
        path.arcTo(400f, 800f, 600f, 1200f, 0f, 90f, false)
    }


    //=======================第二类 辅助设置和算法=============================


}