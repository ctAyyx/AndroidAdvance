package ct.com.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ct.com.ui.course01.Course01Activity
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_course01 -> readyGo(Course01Activity::class)
        }

    }


    private fun readyGo(cls: KClass<*>) {
        startActivity(Intent(this, cls.java))
    }
}