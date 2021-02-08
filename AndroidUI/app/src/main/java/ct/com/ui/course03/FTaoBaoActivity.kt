package ct.com.ui.course03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import ct.com.ui.R
import ct.com.ui.course03.fm.VpAdapter
import ct.com.ui.course03.widget.FNestedScrollView
import ct.com.ui.course03.widget.FRecyclerView
import kotlinx.android.synthetic.main.activity_course03.*

/**
 *
 * 实现 嵌套滑动
 * */
class FTaoBaoActivity : AppCompatActivity() {

    private lateinit var nestedView: FNestedScrollView
    private lateinit var vp: ViewPager2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //testScrollBy()
        testNestScroll()

    }

    private fun testScrollBy() {
        setContentView(R.layout.layout_scroll)
    }

    private fun testNestScroll() {
        setContentView(R.layout.activity_f_tao_bao)
        nestedView = findViewById(R.id.nested_f)
        vp = findViewById(R.id.vp_f)
        vp.adapter = VpAdapter(supportFragmentManager, lifecycle)


        findViewById<FrameLayout>(R.id.fm_head)
            .setOnClickListener {
                Log.e("TAG", "=====>${nestedView.height}")
            }
    }
}