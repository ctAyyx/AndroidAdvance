package ct.com.ui.course03

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ct.com.ui.R
import kotlinx.android.synthetic.main.activity_course03.*

class Course03Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course03)

        rv_course03.adapter = RvAdapter()
        rv_course03.layoutManager = LinearLayoutManager(this)

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnScrollBase -> {
                startActivity(Intent(this, ScrollActivity::class.java))
            }
            else -> {
                startActivity(Intent(this, FTaoBaoActivity::class.java))
            }
        }

    }




}