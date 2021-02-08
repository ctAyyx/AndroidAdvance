package com.ct.framework.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.ct.framework.R
import com.ct.framework.databinding.ActivityBindBinding

/**
 * 初探 DataBinding BR类
 * */
class BindActivity : AppCompatActivity() {

    // 生成一个可观察的数据对象
    private val vo = BindBRVo("宝马").apply { home = "成都" }

    private val live = MutableLiveData<BindBRVo>(vo)
    //双向数据绑定

    private lateinit var binding: ActivityBindBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityBindBinding>(this, R.layout.activity_bind)

        binding.model = vo
        binding.listener = createListener()

        live.observe(this) {
            Log.e("TAG", "---->$it")
        }


    }

    private fun createListener() = View.OnClickListener {
        binding.tvBind01.text = "新的地址"
        binding.tvBind02.text = "奥迪A7"
        Log.e("TAG", "====>$vo")
    }


}