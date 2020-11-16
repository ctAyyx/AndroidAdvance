package com.ct.framework.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct.framework.R
import com.ct.framework.jetpack.adapter.RvCategoryAdapter
import com.ct.framework.jetpack.di.AppModule
import com.ct.framework.jetpack.mvvm.repository.ListRepository
import com.ct.framework.jetpack.mvvm.vm.ListViewModel
import kotlinx.android.synthetic.main.activity_jetpack.*

/**
 * 针对 Jetpack的练习使用
 * */
class JetpackActivity : AppCompatActivity() {

    private val vm: ListViewModel by viewModels {
        ListViewModel.ListVMFactory(
            ListRepository(
                AppModule.getServiceApi()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)


        // doVm01()
        //doVm03()
        btn_01.setOnClickListener {
            //doVm04()
            doVm05()
        }
    }

    //测试 最基本的MVVM设计模式 接口调用一次
    private fun doVm01() {
        vm.detailData.observe(this, Observer {
            Log.e("TAG", "获取到的详情数据:${it}")
        })
        vm.setId("5e777432b8ea09cade05263f")
    }


    //测试 使用Paging分页库
    private fun doVm02() {

    }


    /**
     * 以下测试自定义的数据请求类
     * */
    private fun doVm03() {
        vm.detailData02.observe(this, Observer {
            Log.e("TAG", "获取到自定义数据:${it.status} -- ${it.data}")
        })
        vm.setId("5e777432b8ea09cade05263f")
    }

    /**
     * 优化响应模块
     * */
    @Deprecated("有问题 耦合度高")
    private fun doVm04() {
        vm.detailData03.observe(this, Observer {
            Log.e("TAG", "获取到自定义数据:${it.status} -- ${it.data}")
        })
        vm.setId("5e777432b8ea09cade05263f")
    }


    private fun doVm05() {
        vm.detailData04.observe(this, Observer {
            Log.e("TAG", "获取到doVm05自定义数据:${it.status} -- ${it.data}")
        })

        vm.setId("5e777432b8ea09cade05263f")
    }


    /**
     *
     * 下面的是针对 有分页的数据
     * */

    private lateinit var adapter: RvCategoryAdapter
    private fun initRv() {
        adapter = RvCategoryAdapter()
        rv_01.adapter = adapter
        rv_01.layoutManager = LinearLayoutManager(this)
    }

    private fun doVm06() {

    }

}