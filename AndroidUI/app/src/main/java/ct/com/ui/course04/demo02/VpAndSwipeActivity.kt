package ct.com.ui.course04.demo02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ct.com.ui.R
import kotlinx.android.synthetic.main.activity_vp_and_swipe.*

/**
 *
 *SwipeRefreshLayout 和 ViewPager出现的事件冲突
 * */

class VpAndSwipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vp_and_swipe)
        initView()
    }


    private fun initView() {

        val adapter =
            VpFragmentAdapter(supportFragmentManager, mutableListOf(Fragment01(), Fragment02()))

        vp_course04_02.adapter = adapter

        val adapter1 =
            VpFragmentAdapter(supportFragmentManager, mutableListOf(Fragment01(), Fragment02()))

        vp_course04_03.adapter = adapter1


        swipe_course04.setOnRefreshListener {
            Log.e("TAG", "------------>刷新")
            swipe_course04.isRefreshing = false
        }

        swipe_course04_02.setOnRefreshListener {
            Log.e("TAG", "------------>刷新")
            swipe_course04_02.isRefreshing = false
        }
vp_course04_02.requestDisallowInterceptTouchEvent(true)

    }
}