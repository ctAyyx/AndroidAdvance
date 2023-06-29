package ct.com.ui.course03

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import ct.com.ui.R
import ct.com.ui.course03.scroll.ScrollTextView

/**
 * 滚动控件
 * NestedScrollView源码解析
 * 对OverScroll的使用
 * 对VelocityTracker的使用
 * 对EdgeEffect的使用
 */
class ScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll)
        scroll01()
        springBack01()
    }

    private fun scroll01() {
        val tvScroll = findViewById<ScrollTextView>(R.id.tvBase1)
        tvScroll.setOnClickListener {
            tvScroll.overScrollByCompat(
                0,
                20,
                0,
                tvScroll.scrollY,
                0,
                tvScroll.scrollRange,//100
                0,
                tvScroll.maxOverScrollY,
                false
            )
        }

    }

    private fun springBack01() {
        val btnScroll = findViewById<AppCompatButton>(R.id.btnScroll)
        val btnSpringBack = findViewById<AppCompatButton>(R.id.btnSpringBack)

        val tvScroll = findViewById<ScrollTextView>(R.id.tvBase1)

        btnScroll.setOnClickListener {

        }
        btnSpringBack.setOnClickListener {
            ViewCompat.postInvalidateOnAnimation(tvScroll)
        }
    }


}