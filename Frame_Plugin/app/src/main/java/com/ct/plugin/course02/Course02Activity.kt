package com.ct.plugin.course02

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.ct.plugin.R

/**
 *
 * 加载 插件APk中的Activity 并启动Activity
 * */
class Course02Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course02)

        val path = PluginUtil.getPluginPath(this, "plugins-debug.apk")
        PluginUtil.loadApk(this, path)

        findViewById<AppCompatButton>(R.id.btn_course02_activity)
            .setOnClickListener {
                openPluginsActivity()
            }

        HookUtil.hookAMS()
        HookUtil.hookHandler()
    }


    private fun openPluginsActivity() {

        //这样写会直接抛出 ActivityNotFoundException
        //在我们启动一个Activity的时候 会由AMS查询我们要启动的Activity是否已经注册
        //而插件的 Activity 并没有在我们宿主App中注册

//        val intent = Intent()
//        intent.setClassName("com.ct.plugins", "com.ct.plugins.PluginActivity")
//        startActivity(intent)


        //解决思路
        //在启动插件的Activity的时候
        //我们拦截Intent 修改Intent为启动一个本地的占位Activity
        //在AMS 校验通过后 再修改Intent为原来的Intent

        val intent = Intent()
        intent.setClassName("com.ct.plugins", "com.ct.plugins.PluginActivity")
        //为插件Activity添加一个标记
        intent.putExtra(FLAG_PLUGIN, true)
        startActivity(intent)


    }

}