package ct.com.ui.course02

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout

class MyFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val model = fun(mode: Int) = when (mode) {
            MeasureSpec.EXACTLY -> "EXACTLY"
            MeasureSpec.AT_MOST -> "AT_MOST"
            else -> "UNSPECIFIED"
        }
        Log.e(
            "TAG",
            "测量模式:${model(widthMode)}  ${model(heightMode)} --- ${measuredWidth} -- $measuredHeight"
        )
    }
}