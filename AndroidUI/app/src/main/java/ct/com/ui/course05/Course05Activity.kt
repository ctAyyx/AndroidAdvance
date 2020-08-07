package ct.com.ui.course05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ct.com.ui.R
import ct.com.ui.course05.basics.DrawBasicActivity
import kotlin.reflect.KClass

class Course05Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course05)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_course05_01 -> readyGo(DrawBasicActivity::class)
        }
    }

    private fun readyGo(cls: KClass<*>) {
        startActivity(Intent(this, cls.java))
    }
}