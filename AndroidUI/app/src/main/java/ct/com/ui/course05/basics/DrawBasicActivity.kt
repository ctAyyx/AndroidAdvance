package ct.com.ui.course05.basics

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import ct.com.ui.R

class DrawBasicActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw_basic)
    }

    private fun initView(view: View) {
        val viewBasic = findViewById<CanvasBasicView>(R.id.viewBasic)
        val viewPaint = findViewById<PaintView>(R.id.viewPaint)
        val viewText = findViewById<DrawTextView>(R.id.viewText)
        val viewClip = findViewById<ClipView>(R.id.viewClip)
        val imgClip = findViewById<AppCompatImageView>(R.id.imgClip)

        viewBasic.setVisibility(view.id == R.id.btn01)
        viewPaint.setVisibility(view.id == R.id.btn02)
        viewText.setVisibility(view.id == R.id.btn03)
        viewClip.setVisibility(view.id == R.id.btn04)
        imgClip.setVisibility(view.id == R.id.btn04)


    }

    fun onClick(view: View) {
        initView(view)
    }

    fun View.setVisibility(visibility: Boolean) {
        this.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}