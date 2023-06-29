package ct.com.ui.course10

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OverlayLayoutManager : RecyclerView.LayoutManager() {
    //最大重叠数目
    companion object {
        const val MAX_OVER_SIZE = 4
        const val TRANS_Y_GAP = 40f
        const val SCALE_GAP = 0.05f
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * 确定 ItemView的位置
     * */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {

        //1. 缓存数据
        detachAndScrapAttachedViews(recycler)

        //2. 确定需要布局的数量
        val bottomPosition = if (itemCount <= MAX_OVER_SIZE) 0 else itemCount - MAX_OVER_SIZE

        //3. 复用数据
        for (i in bottomPosition until itemCount) {

            //4. 获取一个ItemView 并添加到布局中
            val scrap = recycler.getViewForPosition(i)
            addView(scrap)

            //5. 测量
            measureChildWithMargins(scrap, 0, 0)
            val mWidth = getDecoratedMeasuredWidth(scrap)
            val mHeight = getDecoratedMeasuredHeight(scrap)

            //6. 布局
            val left = (width - mWidth) / 2
            val top = (height - mHeight) / 2
            val right = left + mWidth
            val bottom = top + mHeight
            layoutDecoratedWithMargins(scrap, left, top, right, bottom)

            //7. 根据角标 平移缩放ItemView
            var level = itemCount - i - 1
            if (i + 1 == bottomPosition + 1)
                level -= 1
            scrap.translationY = level * TRANS_Y_GAP
            scrap.scaleX = 1 - level * SCALE_GAP
            scrap.scaleY = 1 - level *SCALE_GAP


        }

    }

}