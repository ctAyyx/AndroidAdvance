package ct.com.ui.course07

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ct.com.ui.R
import kotlinx.android.synthetic.main.activity_course07.*

/**
 * 绘制 锦鲤鱼
 * 问题
 * 1.save 和 saveLayer的区别
 * 2.Drawable的基本使用
 *
 * */
class Course07Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course07)

        img_course07_fish.setImageDrawable(FishDrawable())
    }
}