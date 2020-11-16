package ct.com.ui.course12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ct.com.ui.R
import ct.com.ui.course12.cs.ConstraintActivity
import kotlinx.android.synthetic.main.activity_course12_1.*
import kotlin.reflect.KClass

/**
 * MD的基本使用
 *
 * */
class Course12Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course12_1)

        btn_course12_1.setOnClickListener {

            readyGo(ConstraintActivity::class)
        }
    }


    private fun readyGo(cls: KClass<*>) {
        startActivity(Intent(this, cls.java))
    }

}