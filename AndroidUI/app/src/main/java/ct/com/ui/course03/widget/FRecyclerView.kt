package ct.com.ui.course03.widget

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {


    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)
        //设置 高度为
        Log.e("TAG", "======>$measuredHeight")
        // setMeasuredDimension(measuredWidth,1997)
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        val result = super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
        Log.e("TAG", "dispatchNestedPreScroll ===> $result")
        return result
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        val result= super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
        Log.e("TAG", "dispatchNestedPreScroll ===> $result")
        return result
    }



}