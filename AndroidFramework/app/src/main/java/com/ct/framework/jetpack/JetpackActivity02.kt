package com.ct.framework.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.toLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.ct.framework.R
import com.ct.framework.jetpack.adapter.RvCategoryAdapter
import com.ct.framework.jetpack.di.AppModule
import com.ct.framework.jetpack.mvvm.paging.test.Test2Repository
import com.ct.framework.jetpack.mvvm.paging.test.Test2VMFactory
import com.ct.framework.jetpack.mvvm.paging.test.Test2ViewModel
import com.ct.framework.jetpack.mvvm.repository.ListRepository
import com.ct.framework.jetpack.mvvm.vm.ListViewModel
import com.ct.framework.jetpack.room.AppDatabase
import com.ct.framework.jetpack.room.dao.GirlDao
import com.ct.framework.jetpack.room.dao.GirlDao_Impl
import kotlinx.android.synthetic.main.activity_jetpack.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * 针对 NetworkBoundResource 在引入Paging后无法适用的情况
 *
 * 1. 直接网络作为单一数据源的情况下 直接获取数据
 * 2. 将数据库作为单一数据源的情况下
 *
 * */
class JetpackActivity02 : AppCompatActivity() {

    private lateinit var adapter: RvCategoryAdapter
    private val vm: Test2ViewModel by viewModels { Test2VMFactory(Test2Repository(AppModule.getServiceApi())) }

    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jetpack)

        db = Room.databaseBuilder(this, AppDatabase::class.java, "framework").build()

        adapter = RvCategoryAdapter()
        rv_01.adapter = adapter
        rv_01.layoutManager = LinearLayoutManager(this)


        btn_02.setOnClickListener {
            //loadData()
            loadData2()
        }

    }

    /**
     * 直接从网络获取数据
     * */
    private fun loadData() {
        val resource = vm.getGirlList()

        resource.data?.observe(this, Observer {
            adapter.submitList(it)
        })

        resource.networkState?.observe(this, Observer {
            Log.e("TAG", "数据请求状态:$it")
        })
    }

    /**
     * 从数据库获取数据
     * */

    private fun loadData2() {
        val dao = db.girlDao()
        val resource = vm.getGirlListByDB(dao)

        resource.data?.observe(this, Observer {
            adapter.submitList(it)
        })

        resource.networkState?.observe(this, Observer {
            Log.e("TAG", "网络请求状态:$it")
        })

        resource.refreshState?.observe(this, Observer {
            Log.e("TAG", "数据刷新状态:$it")
        })

    }

}