package ct.com.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ct.com.ui.course01.Course01Activity
import ct.com.ui.course02.SkinActivity
import ct.com.ui.course04.Course04Activity
import ct.com.ui.course05.Course05Activity
import ct.com.ui.course07.Course07Activity
import ct.com.ui.course08.Course08Activity
import ct.com.ui.course12.Course12Activity
import ct.com.ui.course15.Course15Activity
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_course01 -> readyGo(Course01Activity::class)
            R.id.btn_course02 -> readyGo(SkinActivity::class)
            R.id.btn_course04 -> readyGo(Course04Activity::class)
            R.id.btn_course05 -> readyGo(Course05Activity::class)
            R.id.btn_course07 -> readyGo(Course07Activity::class)
            R.id.btn_course08 -> readyGo(Course08Activity::class)
            R.id.btn_course12 -> readyGo(Course12Activity::class)
            R.id.btn_course13 -> readyGo(Course05Activity::class)
            R.id.btn_course14 -> readyGo(Course05Activity::class)
            R.id.btn_course15 -> readyGo(Course15Activity::class)

        }

    }


    private fun readyGo(cls: KClass<*>) {
        startActivity(Intent(this, cls.java))
    }
}