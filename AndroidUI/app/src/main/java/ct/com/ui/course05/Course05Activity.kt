package ct.com.ui.course05

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import ct.com.ui.R
import ct.com.ui.course05.basics.DrawBasicActivity
import kotlin.reflect.KClass

class Course05Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course05)
        clipBounds()
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_course05_01 -> readyGo(DrawBasicActivity::class)
        }
    }

    private fun readyGo(cls: KClass<*>) {
        startActivity(Intent(this, cls.java))
    }


    /**
     * 针对clipBounds的使用
     */
    private val rect = Rect()
    private fun clipBounds() {
        val tvClipBounds = findViewById<AppCompatTextView>(R.id.tvClipBounds)

        tvClipBounds.setOnClickListener {
            tvClipBounds.clipBounds = rect
            ValueAnimator.ofFloat(0f, 1f).apply {
                addUpdateListener {
                    val value = it.animatedValue as Float
                    rect.set(
                        0,
                        0,
                        (tvClipBounds.measuredWidth * value).toInt(),
                        tvClipBounds.measuredHeight
                    )
                    tvClipBounds.clipBounds = rect
                }
                duration = 3000
                start()
            }
        }


    }
}