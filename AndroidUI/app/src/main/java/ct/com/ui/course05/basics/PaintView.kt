package ct.com.ui.course05.basics

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import ct.com.ui.R

/**
 * Paint的使用详解
 *Paint 的 API 大致可以分为 4 类：
 * 颜色
 * 效果
 * drawText() 相关
 * 初始化
 * */
class PaintView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var centerX = 0f
    private var centerY = 0f

    private val radius = 600f
    private val mPaint by lazy {
        Paint()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        centerX = width / 2f
        centerY = height / 2f
        //设置着色器
        setShader()
//        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaint)
        canvas.drawCircle(centerX, centerY, radius, mPaint)
    }

    //=========================颜色===========================
    /**
     * 直接给画笔设置颜色
     * */
    private fun setColor() {
        mPaint.color = Color.RED
        mPaint.setARGB(0xff, 0xff, 0xff, 0x00)
    }

    /**
     * 使用着色器 shader
     * 着色器设置的是一个颜色方案，或者说是一套着色规则。
     * 当设置了 Shader 之后，Paint 在绘制图形和文字时就不使用 setColor/ARGB() 设置的颜色了，而是使用 Shader 的方案中的颜色。
     *
     *
     * Shader.TileMode 设置着色器的端点范围之外的着色规则
     *        CLAMP 直接沿用端点处的颜色
     *        MIRROR 是镜像模式
     *        REPEAT 是重复模式
     * */
    private fun setShader() {
        //获取 线性渐变的Shader
        //val shader = getLinearGradient()

        //获取 辐射渐变的Shader
        val shader = getRadialGradient()

        //获取 扫描渐变的Shader
        //val shader = getSweepGradient()

        //获取 Bitmap的Shader
        //val shader = getBitmapShader()

        //获取 混合模式的Shader
        //val shader = getComposeShader()
        mPaint.shader = shader
    }

    /**
     * 获取 线性渐变的着色器
     * */
    private fun getLinearGradient(): Shader {

        // x0 y0 端点的起始位置
        // x1 y1 端点的结束位置

        val shader = LinearGradient(
            400f, 400f, 500f, 600f, Color.parseColor("#E91E63"),
            Color.parseColor("#2196F3"), Shader.TileMode.CLAMP
        )
        return shader
    }

    /**
     * 获取 辐射渐变
     * */
    private fun getRadialGradient(): Shader {
//        val shader = RadialGradient(
//            centerX, centerY, radius, Color.parseColor("#E91E63"),
//            Color.parseColor("#2196F3"), Shader.TileMode.MIRROR
//        )

        val shader = RadialGradient(
            centerX, centerY, radius,
            intArrayOf(
                Color.WHITE,
                Color.parseColor("#00F3CE"),
                Color.parseColor("#004AF3"),
                Color.parseColor("#262B40")
            ) ,floatArrayOf(0.3f, 0.5f, 0.7f, 1f),Shader.TileMode.MIRROR

        )

        return shader
    }

    /**
     * 获取 扫描渐变
     * */
    private fun getSweepGradient(): Shader {
        val shader = SweepGradient(
            centerX, centerY, Color.parseColor("#E91E63"),
            Color.parseColor("#2196F3")
        )

        return shader
    }

    /**
     * 获取 bitmap着色器
     * */
    private fun getBitmapShader(): Shader {

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.xm)

        val shader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)

        return shader

    }

    /**
     * 获取 混合的着色器
     * */
    private fun getComposeShader(): Shader {

        val shader1 = getBitmapShader()
        val shader2 = getSweepGradient()

        //shader1 目标像素
        //shader2 源像素
        //这里的混合模式 参见: https://developer.android.google.cn/reference/android/graphics/PorterDuff.Mode?hl=en#DARKEN
        val shader = ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_IN)
        return shader

    }

}