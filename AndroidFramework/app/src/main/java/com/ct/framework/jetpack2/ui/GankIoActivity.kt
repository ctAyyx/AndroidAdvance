package com.ct.framework.jetpack2.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.room.Room
import com.ct.framework.R
import com.ct.framework.jetpack2.base.AppExecutors
import com.ct.framework.jetpack2.base.Status
import com.ct.framework.jetpack2.db.GankDatabase
import com.ct.framework.jetpack2.di.AppModule
import com.ct.framework.jetpack2.repository.GankIoRepository
import com.ct.framework.jetpack2.vm.GankIoViewModel
import com.ct.framework.jetpack2.vm.GankIoViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 *
 * */
class GankIoActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    companion object {
        var lau = "th"
    }

//    private val db by lazy {
//        Room.databaseBuilder(this, GankDatabase::class.java, "gank_th")
//            .fallbackToDestructiveMigration()
//            .build()
//    }

    private lateinit var db: GankDatabase

    /**
     * 这里应该使用DIO 注入
     * */
    private val viewModel by viewModels<GankIoViewModel> {
        GankIoViewModelFactory(
            GankIoRepository(
                appExecutors = AppExecutors(),
                dao = db.getGankIoDao(),
                serviceApi = AppModule.getServiceApi()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gank_io)

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_gankIo -> {
                useRetrofit()
            }
            R.id.btn_gankIo_01 -> {
                getDetail()
            }
            R.id.btn_gankIo_02 -> {
                getDetailWithStatus()
            }
            R.id.btn_gankIo_03 -> {
                useRetrofitWith()
            }

            R.id.btn_gankIo_04 -> {
                lau = "th"
                db = Room.databaseBuilder(this, GankDatabase::class.java, "gank_${lau}")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            R.id.btn_gankIo_05 -> {
                lau = "zh"
                db = Room.databaseBuilder(this, GankDatabase::class.java, "gank_${lau}")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

    /**
     * 获取 详情数据
     * 验证 不需要分页的数据请求封装
     * */
    private fun getDetail() {

        viewModel.getGirlDetail("5e777432b8ea09cade05263f")
            .observe(this) {

                Log.e("TAG", "获取详情的数据:${Gson().toJson(it)}")

            }
    }


    /**
     * 获取详情
     * 监听请求状态
     * */
    private fun getDetailWithStatus() {
        val model = viewModel.getGirlDetailWithStatus("5e777432b8ea09cade05263f")
        model.data?.observe(this) {
            Log.e("TAG", "获取详情的数据:$it")
        }
        model.networkState?.observe(this) {
            Log.e("TAG", "获取请求的状态:$it")
            if (it.status == Status.ERROR) {
                model.retry.invoke()
            }
        }


    }


    private fun useRetrofit() {

        launch {
            val model = AppModule.getServiceApi()
                .getDetail02("5e777432b8ea09cade05263f")

            Log.e("TAG", "获取的数据:${Gson().toJson(model)}")
        }
    }


    //================== 我们使用 Retrofit 的新特性 =================
    // Retrofit 和 协程 的共同使用
    private fun useRetrofitWith() {
        val result = viewModel.getDetailWithCoroutines("5e777432b8ea09cade05263f")
        result.data?.observe(this) {
            Log.e("TAG", "通过协程获取数据:$it")
        }
        result.networkState?.observe(this) {
            Log.e("TAG", "通过协程获取状态:$it")
        }
    }


}