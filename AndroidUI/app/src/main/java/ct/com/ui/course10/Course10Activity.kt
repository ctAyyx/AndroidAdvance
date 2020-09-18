package ct.com.ui.course10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import ct.com.ui.R
import kotlinx.android.synthetic.main.activity_course10.*

/**
 * 自定义 LayoutManager的实现
 * */

class Course10Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course10)

        val adapter = Course10RvAdapter()
        rv_course10.layoutManager = OverlayLayoutManager()
        rv_course10.adapter = adapter


        val helper = ItemTouchHelper(OverlayTouchHelper(adapter))
        //helper.attachToRecyclerView(rv_course10)

    }
}