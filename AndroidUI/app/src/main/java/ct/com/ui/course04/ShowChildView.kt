package ct.com.ui.course04

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import java.util.*


/**
 * 验证 ViewGroup 对点击事件分发子View的获取
 * */
class ShowChildView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    fun showChildViewList() {


        val childrenCount: Int = childCount
        val mPreSortedChildren = ArrayList<View>(childrenCount)
        for (i in 0 until childrenCount) {
            // add next child (in child order) to end of list
            val childIndex: Int = i
            val nextChild: View = getChildAt(childIndex)
            val currentZ = nextChild.z

            // insert ahead of any Views with greater Z
            var insertIndex = i
            while (insertIndex > 0 && mPreSortedChildren.get(insertIndex - 1).getZ() > currentZ) {
                insertIndex--
            }
            mPreSortedChildren.add(insertIndex, nextChild)
        }

        mPreSortedChildren.forEach {
            Log.e("TAG", "子View:$it")
        }
    }


}