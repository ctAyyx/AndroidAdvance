package ct.com.ui.course09

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout

class MyLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val model = fun(mode: Int) = when (mode) {
            MeasureSpec.EXACTLY -> "EXACTLY"
            MeasureSpec.AT_MOST -> "AT_MOST"
            else -> "UNSPECIFIED"
        }
//        Log.e(
//            "TAG",
//            "${this.hashCode()}测量模式:${model(widthMode)}  ${model(heightMode)} --- $measuredWidth -- $measuredHeight"
//        )

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

}