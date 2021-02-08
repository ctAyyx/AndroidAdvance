package ct.com.ui.course04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import ct.com.ui.R
import ct.com.ui.course04.demo01.LvAndVpActivity
import ct.com.ui.course04.demo02.VpAndSwipeActivity
import kotlin.reflect.KClass

class Course04Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course04)


    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_course04_01 -> readyGo(LvAndVpActivity::class)
            R.id.btn_course04_02 -> readyGo(VpAndSwipeActivity::class)
            R.id.btn_course04_03 -> {
                findViewById<ShowChildView>(R.id.showChild).showChildViewList()
            }
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    private fun readyGo(cls: KClass<*>) {
        startActivity(Intent(this, cls.java))
    }
}