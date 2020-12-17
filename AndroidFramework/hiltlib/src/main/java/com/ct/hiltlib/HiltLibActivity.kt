package com.ct.hiltlib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ct.hiltlib.vo.Contacts
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltLibActivity : AppCompatActivity() {

    @Inject
    lateinit var contacts: Contacts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt_lib)

        Log.e("TAG", "模块的Contacts:$contacts")
    }
}