package ct.com.ui.course14

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.SimpleArrayMap
import androidx.core.view.ViewCompat
import ct.com.ui.R

/**
 * 协调者布局
 * */
class Course14Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val map = SimpleArrayMap<String, String>()

//        map.put("1", "value1")
//        map.put("2", "value2")
//        map.put("3", "value3")
//
//        Log.e("TAG","==?>${ map.keyAt(0)}")
//        Log.e("TAG","==?>${ map.keyAt(1)}")
//        Log.e("TAG","==?>${ map.keyAt(2)}")

        setContentView(R.layout.activity_course14)


    }

}