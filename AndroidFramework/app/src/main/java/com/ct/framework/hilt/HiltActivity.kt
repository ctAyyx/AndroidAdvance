package com.ct.framework.hilt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ct.framework.R
import com.ct.framework.hilt.alias.CarAD
import com.ct.framework.hilt.alias.CarBM
import com.ct.framework.hilt.point.UserManager
import com.ct.framework.hilt.vm.GankViewModel
import com.ct.framework.hilt.vo.Car
import com.ct.framework.hilt.vo.User
import com.ct.hiltlib.HiltLibActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_hilt.*
import retrofit2.Retrofit
import javax.inject.Inject

/**
 *
 * */
@AndroidEntryPoint
class HiltActivity : AppCompatActivity() {


    @CarBM
    @Inject
    lateinit var user: User

    @CarAD
    @Inject
    lateinit var user2: User

    @Inject
    lateinit var retrofit: Retrofit

    private val viewModel: GankViewModel by viewModels()


    @Inject
    @CarBM
    lateinit var bmCar: Car

    @Inject
    @CarAD
    lateinit var adCar: Car

    @Inject
    lateinit var userManager: UserManager

    @Inject
    lateinit var userManager2: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt)

        Log.e("TAG", "${user}User:${user.print()}")
        Log.e("TAG", "${user2}User:${user2.print()}")
        Log.e("TAG", "Retrofit:${retrofit}")
        Log.e("TAG", "ViewModel:${viewModel}")

        Log.e("TAG", "BMCar:$bmCar   ${bmCar.getCarName()}")
        Log.e("TAG", "BMCar:$adCar   ${adCar.getCarName()}")


        Log.e("TAG", "UserManager:$userManager}")
        Log.e("TAG", "UserManager:$userManager2}")

        userManager.getMyCar()

        btn_hilt_01.setOnClickListener {
            startActivity(Intent(this, HiltLibActivity::class.java))
        }
    }
}