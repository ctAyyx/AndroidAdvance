package com.ct.plugin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ct.plugin.course01.Course01Activity
import com.ct.plugin.course02.Course02Activity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onCourse01(view: View) {
        startActivity(Intent(this, Course01Activity::class.java))
    }

    fun onCourse02(view: View) {
        startActivity(Intent(this, Course02Activity::class.java))
    }
}