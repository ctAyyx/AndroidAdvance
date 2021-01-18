package com.ct.framework.lifecycle

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ct.framework.R
import kotlinx.android.synthetic.main.activity_lifecycle.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 自定义 LifecycleOwner
 * 这里保持生命周期和 Activity的一致
 * */
class LifecycleActivity : AppCompatActivity() {

    private lateinit var lifecycleHolder: MyLifecycleHolder
    private lateinit var lifecycleObserver: MyLifecycleObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        lifecycleHolder = MyLifecycleHolder()
        lifecycleHolder.onCreate()


        lifecycleObserver = MyLifecycleObserver()
        lifecycleHolder.lifecycle.addObserver(lifecycleObserver)


//        lifecycleHolder.apply {
//
//            lifecycle.myLifeScope.launch {
//                while (true) {
//                    delay(1000L)
//                    Log.e("TAG", "现在是在自定义的作用域中:$this")
//                }
//            }
//        }


        btn_url.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data =
                Uri.parse("http://129.226.123.108:3007/transition.html/?lang=en&data=ZHJxWFJlU1BMUktDTFFid1i7dJzZ4haKLhav1I2RodoNYGsEMnqnJDlxkA5OEDUgwz-Erq2MVwRABquQ6LSZL4CpeZDwW5afya8PkOJ8pIk=")
            startActivity(intent)

        }
    }

    override fun onStart() {
        super.onStart()
        lifecycleHolder.onStart()
    }

    override fun onResume() {
        super.onResume()
        lifecycleHolder.onResume()
    }

    override fun onPause() {
        super.onPause()
        lifecycleHolder.onPause()
    }

    override fun onStop() {
        super.onStop()
        lifecycleHolder.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleHolder.onDestroy()
    }

}