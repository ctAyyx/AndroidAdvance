package ct.com.ui.course09.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 粘性头部
 * 1.确定头部
 * */
class StickHeaderItemDecoration(private val mStickHeaderInf: StickHeaderInterface) :
    RecyclerView.ItemDecoration() {

    private val mHeaderHeight = 80
    private val mLineHeight = 5
    private val rect: Rect = Rect()
    private val mPaint = Paint()
    private val mTextPaint = Paint()
    private val mTextOffset = 10F

    private var paddingTop = 0

    /**
     * 设置ItemView的偏移量
     *
     * 涉及到了MeasureSpec
     * RecyclerView是MATCH_PARENT的宽度
     * 如果ItemView的LayoutParams的宽度为
     *
     * MATCH_PARENT -> 则给定的测量模式为 EXACTLY 给定的宽度就是 RecyclerView的宽度减去偏移量 剩下的值
     * EXACTLY      -> 则给定的测量模式为 EXACTLY 宽度为ItemView定义的宽度
     * WRAP_CONTENT -> 则给定的测量模式为 AT_MOST
     *
     * */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (mStickHeaderInf.isHeader(position, parent)) {
            outRect.top = mHeaderHeight
        } else {
            outRect.top = mLineHeight
        }
        paddingTop = parent.paddingTop
    }

    /**
     * 绘制 分割线
     * */
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val count = parent.childCount
        for (i in 0 until count) {
            val childView = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(childView)
            if (mStickHeaderInf.isHeader(position, parent)) {
                //是头部
                drawLine(canvas, childView, position, true)
            } else {
                //不是头部
                drawLine(canvas, childView, position, false)
            }
        }
    }

    /**
     * 思路 LinearLayoutManger
     * 1.获取第一个显示的ItemView
     * 2.判断它的下一个ItemView是否是头部
     * 3.计算头部和粘性头部距离
     * */
    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val position = layoutManager.findFirstVisibleItemPosition()
            rect.bottom = mHeaderHeight + paddingTop
            val itemCount = parent.adapter?.itemCount ?: 0
            if ((position + 1) < itemCount && mStickHeaderInf.isHeader(position + 1, parent)) {
                //下一个ItemView是头部
                val itemView = layoutManager.findViewByPosition(position + 1)
                if (itemView != null) {
                    val bottom = Math.min(rect.bottom, itemView.top - mHeaderHeight)
                    rect.bottom = bottom
                }
            }
            rect.top = rect.bottom - mHeaderHeight
            mPaint.color = Color.RED
            canvas.save()
            canvas.clipRect(rect.left, Math.max(rect.top, paddingTop), rect.right, rect.bottom)
            canvas.drawRect(rect, mPaint)
            drawHeadText(canvas, mStickHeaderInf.providerHeaderModel(position))
            canvas.restore()
        }

    }


    private fun drawLine(canvas: Canvas, view: View, position: Int, isHead: Boolean = false) {
        val left = view.left
        val right = left + view.width
        val bottom = view.top
        val top = if (isHead) bottom - mHeaderHeight else bottom - mLineHeight
        rect.top = top
        rect.left = left
        rect.right = right
        rect.bottom = bottom
        canvas.save()
        canvas.clipRect(rect.left, Math.max(rect.top, paddingTop), rect.right, rect.bottom)
        if (isHead) {
            mPaint.color = Color.RED

            canvas.drawRect(rect, mPaint)
            drawHeadText(canvas, mStickHeaderInf.providerHeaderModel(position))

        } else {
            mPaint.color = Color.YELLOW
            canvas.drawRect(rect, mPaint)
        }
        canvas.restore()

    }

    private fun drawHeadText(canvas: Canvas, drawText: String) {
        mTextPaint.textSize = 40f
        mTextPaint.color = Color.WHITE
        mTextPaint.style = Paint.Style.FILL_AND_STROKE

        //计算 文本居中 baseline的坐标
        val centerY = rect.bottom - rect.height() / 2f
        canvas.drawText(drawText, mTextOffset, getTextY(centerY), mTextPaint)
    }

    private fun getTextY(centerY: Float): Float {
        val ascent = mTextPaint.ascent()
        val descent = mTextPaint.descent()
        return centerY - (descent + ascent) / 2f

    }


}