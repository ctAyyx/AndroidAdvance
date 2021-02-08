package com.ct.framework.jetpack2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.room.Room
import com.ct.framework.R
import com.ct.framework.databinding.ActivityUserInfoBinding
import com.ct.framework.jetpack2.db.GankDatabase
import com.ct.framework.jetpack2.db.dao.UserInfoDao
import com.ct.framework.jetpack2.repository.UserInfoRepository
import com.ct.framework.jetpack2.vm.UserInfoViewModel
import com.ct.framework.jetpack2.vm.UserInfoViewModelFactory
import com.ct.framework.jetpack2.vo.UserInfo
import kotlinx.coroutines.launch

class UserInfoActivity : AppCompatActivity() {

    private lateinit var db: GankDatabase
    private lateinit var binding: ActivityUserInfoBinding
    private lateinit var dao: UserInfoDao
    private lateinit var repository: UserInfoRepository
    private lateinit var viewModel: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)

        db = Room.databaseBuilder(this, GankDatabase::class.java, "userInfo_ui")
            .fallbackToDestructiveMigration()
            .build()

        dao = db.getUserInfoDao()

        repository = UserInfoRepository(dao)
        viewModel = ViewModelProvider(
            this,
            UserInfoViewModelFactory(repository)
        ).get(UserInfoViewModel::class.java)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_userInfo_01 -> {
                upUserInfo()
            }
            R.id.btn_userInfo -> {
                insertUserInfo()
            }
            R.id.btn_userInfo_02 -> {
                getUserInfoByLiveData()
            }
            R.id.btn_userInfo_03 -> {
                getUserInfoByNor()
            }
            R.id.btn_userInfo_04 -> {
                getUserInfoListByLiveData()
            }
            R.id.btn_userInfo_05 -> {
                getUserInfoByNet()
            }
        }
    }

    private fun insertUserInfo() = lifecycleScope.launch {
        val user = UserInfo("1", "张三", 10)
        dao.insertUsers(mutableListOf(user))
    }


    private fun upUserInfo() = lifecycleScope.launch {
        val user = UserInfo("1", "李四", 20)
        dao.upUserInfo(user)
    }

    private fun getUserInfoByLiveData() {
        dao.getUserInfo("1")
            .observe(this) {
                binding.tvUserInfo.text = it.toString()
            }
    }

    private fun getUserInfoByNor() = lifecycleScope.launch {
        val user = dao.getUserInfoBbyNor("1")
        binding.tvUserInfo.text = user.toString()
    }

    private fun getUserInfoListByLiveData() {
        dao.getAllUsers().observe(this) {
            binding.tvUserInfo.text = it.toString()
        }
    }


    private fun getUserInfoByNet() {
        val resource = viewModel.getUserInfoByNet("1")

        resource.data?.observe(this) {
            binding.tvUserInfo.text = it.toString()
        }

        resource.networkState?.observe(this) {
            Log.e("TAG", "====>$it")
        }

    }

}