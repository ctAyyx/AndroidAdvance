package com.ct.plugins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class PluginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)
        Log.e("TAG", "我是插件Activity")
    }
}