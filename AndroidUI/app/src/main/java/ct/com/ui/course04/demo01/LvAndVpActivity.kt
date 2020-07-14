package ct.com.ui.course04.demo01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ct.com.ui.R
import ct.com.ui.course04.demo02.VpFragmentAdapter
import kotlinx.android.synthetic.main.activity_lv_and_vp.*

class LvAndVpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lv_and_vp)

        vp_course04_01.adapter = VpFragmentAdapter(
            supportFragmentManager, mutableListOf(
                ListFragment(),
                ListFragment()
            )
        )
    }
}