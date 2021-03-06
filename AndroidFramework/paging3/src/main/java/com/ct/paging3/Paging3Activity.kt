package com.ct.paging3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.bumptech.glide.Glide
import com.ct.paging3.adapter.CategoryAdapter
import com.ct.paging3.db.AppDatabase
import com.ct.paging3.di.AppModule
import com.ct.paging3.vm.CategoryViewModel
import com.ct.paging3.vm.CategoryViewModelFactory
import kotlinx.android.synthetic.main.activity_paging3.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.concurrent.thread


class Paging3Activity : AppCompatActivity(), CoroutineScope by MainScope() {

    private val adapter = CategoryAdapter()

    private lateinit var db: AppDatabase

    private lateinit var vm: CategoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging3)
        db = Room.databaseBuilder(this, AppDatabase::class.java, "Paging_db").build()
        vm =
            ViewModelProvider(
                this,
                CategoryViewModelFactory(AppModule.getServiceApi(), db.girlDao())
            ).get(
                CategoryViewModel::class.java
            )

        swipe_paging3.isEnabled = false
        subscribeUi02()
    }


    /**
     * 将数据库 或 网络任意一个作为数据来源
     * */
    private fun subscribeUi() {
        rv_paging3.layoutManager = LinearLayoutManager(this)
        rv_paging3.adapter = adapter

        vm.flow.observe(this@Paging3Activity, Observer {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        })

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { status ->
                Log.e("TAG", "数据的加载状态:${status}")
            }
        }


    }

    /**
     * 将数据库作为唯一数据来源
     * */
    private fun subscribeUi02() {
        rv_paging3.layoutManager = LinearLayoutManager(this)
        rv_paging3.adapter = adapter


//        launch {
//            val dao = db.girlDao()
//            val allList = dao.getAllCategory()
//            val size = dao.getCategorySize()
//            Log.e("TAG", "$size 数据库里面的数据:${allList}")
//
//        }
//
//
//        launch {
//            val allList = db.girlDao().getCategory("", "第75期")
//            Log.e("TAG", "数据库里面查询的数据:${allList}")
//        }

        vm.pager.observe(this) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }

        }

        swipe_paging3.isEnabled = true
        swipe_paging3.setOnRefreshListener {
            adapter.refresh()
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { status ->
                Log.e("TAG", "数据请求状态:${status}")
                swipe_paging3.isRefreshing = status.refresh is LoadState.Loading
            }

        }

    }

    fun onScheme(view: View) {

        thread(start = true) {
            Thread.sleep(3000)
                val uri = "frame://www.google.com:8080/MainActivity?name=张三&age=10"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
                Log.e("TAG","去启动深度链接")

        }


    }


}