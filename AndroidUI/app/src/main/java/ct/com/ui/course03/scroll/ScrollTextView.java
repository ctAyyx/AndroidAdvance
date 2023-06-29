package ct.com.ui.course03.scroll;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;

public class ScrollTextView extends AppCompatTextView {

    private final static boolean DEBUG = true;
    private final static String TAG = "ScrollTextView";

    private OverScroller mScroller;

    public ScrollTextView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public ScrollTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ScrollTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mScroller = new OverScroller(context);
    }

    /**
     * NestScrollView中的方法
     * <p>
     * 这里值分析垂直滚动
     */
    public boolean overScrollByCompat(int deltaX, int deltaY,
                                      int scrollX, int scrollY,
                                      int scrollRangeX, int scrollRangeY,
                                      int maxOverScrollX, int maxOverScrollY,
                                      boolean isTouchEvent) {
        final int overScrollMode = getOverScrollMode();
        final boolean canScrollHorizontal =
                computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        final boolean canScrollVertical =
                computeVerticalScrollRange() > computeVerticalScrollExtent();
        final boolean overScrollHorizontal = overScrollMode == View.OVER_SCROLL_ALWAYS
                || (overScrollMode == View.OVER_SCROLL_IF_CONTENT_SCROLLS && canScrollHorizontal);
        // 是否支持垂直方向的弹性效果
        final boolean overScrollVertical = true;

        int newScrollX = scrollX + deltaX;
        if (!overScrollHorizontal) {
            maxOverScrollX = 0;
        }

        // 需要滚动到的Y位置
        int newScrollY = scrollY + deltaY;

        //如果不支持弹性效果 则标记弹性距离为0
        if (!overScrollVertical) {
            maxOverScrollY = 0;
        }

        // Clamp values if at the limits and record
        final int left = -maxOverScrollX;
        final int right = maxOverScrollX + scrollRangeX;

        // 向下滚动能到的最小坐标
        final int top = -maxOverScrollY;
        // 向上滚动能到的最大坐标
        final int bottom = maxOverScrollY + scrollRangeY;

        boolean clampedX = false;
        if (newScrollX > right) {
            newScrollX = right;
            clampedX = true;
        } else if (newScrollX < left) {
            newScrollX = left;
            clampedX = true;
        }

        // 是否使用回弹效果
        boolean clampedY = false;
        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
        } else if (newScrollY < top) {
            newScrollY = top;
            clampedY = true;
        }

        // 使用 并且没有父布局接收滚动事件
        //&& !hasNestedScrollingParent(ViewCompat.TYPE_NON_TOUCH)
        if (clampedY || isTouchEvent) {
            // 记录回弹
            boolean result = mScroller.springBack(newScrollX, newScrollY, 0, 0, 0, getScrollRange());
            if (DEBUG)
                Log.e(TAG, "记录回弹" + result + "---newScrollY:" + newScrollY);
        }

        //继续滚
        if (DEBUG)
            Log.e(TAG, "滚动" + newScrollY);
        onOverScrolled(newScrollX, newScrollY, clampedX, clampedY);

        return clampedX || clampedY;
    }


    /**
     * 获取 可以滚动的最大区间
     *
     * @return
     */
    public int getScrollRange() {
        return 100;//getHeight();
    }

    /**
     * 获取 可以超过滚动区间的最大距离
     *
     * @return
     */
    public int getMaxOverScrollY() {
        return 60;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        super.scrollTo(scrollX, scrollY);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (DEBUG) {
            Log.e(TAG, "====>>>>computeScroll:" + mScroller.isFinished());
        }

        if (mScroller.isFinished())
            return;

        mScroller.computeScrollOffset();
        int scrollY = mScroller.getCurrY();
        int startY = mScroller.getStartY();
        int finalY = mScroller.getFinalY();
        Log.e(TAG, "===>>scrollY:" + scrollY + " startY:" + startY + " finalY:" + finalY);

        // 执行这个方法后 只有里面的springBack执行了 mScroller.isFinished才是false
        overScrollByCompat(0, -10, 0, getScrollY(), 0, getScrollRange(), 0, getMaxOverScrollY(), false);

        if (!mScroller.isFinished())
            ViewCompat.postInvalidateOnAnimation(this);


    }


}
