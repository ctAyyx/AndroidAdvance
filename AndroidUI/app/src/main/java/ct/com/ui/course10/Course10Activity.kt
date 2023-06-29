package ct.com.ui.course10

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ct.com.ui.R
import kotlinx.android.synthetic.main.activity_course10.*

/**
 * 自定义 LayoutManager的实现
 * */

class Course10Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course10)
        btn_course10_recycle.setOnClickListener {
            checkRecycle()
        }
        btn_course10_manager.setOnClickListener {
            customLayoutManager()
        }

        btnCurse10List.setOnClickListener {
            customListLayoutManager()
        }

    }

    /**
     *  梳理 RecyclerView 缓存 和 复用
     */
    private fun checkRecycle() {
        openDebug()
        val adapter = Course10RvAdapter1()
        rv_course10.layoutManager = LinearLayoutManager(this)
        rv_course10.adapter = adapter

        rv_course10.afterMeasured {
            Log.e("TAG", "测量的高度为:$measuredHeight")
        }
    }

    inline fun <T : View> T.afterMeasured(crossinline function: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    function()
                }
            }
        })
    }


    private fun customLayoutManager() {

        val adapter = Course10RvAdapter()
        rv_course10.layoutManager = OverlayLayoutManager()
        rv_course10.adapter = adapter
        val helper = ItemTouchHelper(OverlayTouchHelper(adapter))
        helper.attachToRecyclerView(rv_course10)
    }


    /**
     * 定义一个列表的LayoutManager
     */
    private fun customListLayoutManager() {

        rv_course10.apply {
            adapter = Course10RvAdapter1()
            layoutManager = TwoSideLayoutManager()
        }
    }

    private fun useTouchHelper() {
        //
        //
    }

    private fun openDebug() {
        val rvCls = RecyclerView::class.java
        val debugField = rvCls.getDeclaredField("DEBUG")
        debugField.isAccessible = true
        Log.e("TAG", "===>${debugField.get(null)}")
        debugField.set(null, true)
        Log.e("TAG", "===>${debugField.get(null)}")
    }
}