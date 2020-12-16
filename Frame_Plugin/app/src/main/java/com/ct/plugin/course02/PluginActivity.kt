package com.ct.plugin.course02

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class PluginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG","我是宿主的插件Activity")

    }

}