package ct.com.ui.course01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ct.com.ui.R

/**
 * 第一节:什么是自定义View 什么是高级UI
 *
 * onMeasure onLayout
 *
 * onDrawl
 *
 * */
class Course01Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course01)


        findViewById<View>(R.id.viewTest).apply {
            post {
                Log.e("TAG", "===?${this.width}")
            }
        }

        findViewById<View>(R.id.fmTest).apply {
            post {
                Log.e("TAG", "fmTest===?${this.width}")
            }
        }
    }
}