package com.ct.jetpack


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ct.jetpack.coroutine.CoroutineActivity
import com.ct.jetpack.course04.Jetpack01Activity
import com.ct.jetpack.course04.LiveDataBus
import kotlin.reflect.KClass

/**
 * */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LiveDataBus.get().with("Sticker", String::class.java)
            .observe(this) {
                Log.e("TAG", "$this-->LiveDataBus粘性事件:$it")
            }
    }


    fun onClick(view: View) {

        when (view.id) {
            R.id.btn_kt -> {
                readyGo(CoroutineActivity::class)
            }
            R.id.btn_liveData_sticker -> {
                toTestLiveDataBus()
            }
            R.id.btn_course04 -> {
                readyGo(Jetpack01Activity::class)
            }
        }

    }


    private fun toTestLiveDataBus() {
        LiveDataBus.get().with("Sticker", String::class.java).value = "测试粘性事件"
    }


}


fun AppCompatActivity.readyGo(cls: KClass<*>) {
    val intent = Intent(this, cls.java)
    startActivity(intent)
}