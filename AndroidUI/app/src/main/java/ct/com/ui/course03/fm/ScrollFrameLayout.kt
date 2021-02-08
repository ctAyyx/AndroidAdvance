package ct.com.ui.course03.fm

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout

class ScrollFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.e("TAG", "===>$measuredWidth  $measuredHeight")
        val params = childVIew.layoutParams
        params.height = 2097 * 2
        childVIew.layoutParams = params

    }

    lateinit var childVIew: View
    override fun onFinishInflate() {
        super.onFinishInflate()
        setOnClickListener {
            Log.e("TAG", "滚动:$scrollY")
            scrollBy(0, 100)
        }
        childVIew = getChildAt(0)
    }

}