package com.ct.framework.nav.host01

import android.util.Log
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ct.framework.R

class BFragment : BaseFragment() {

    override fun initBtn01Text() = "导航到CFragment"

    override fun onBtn01Click(view: View) {
        view.findNavController()
            .navigate(R.id.action_BFragment_to_CFragment)

    }

    override fun initView(root: View) {
        val previousEntry = findNavController().currentBackStackEntry
        val handle = previousEntry?.savedStateHandle ?: return

        handle.getLiveData<String>("").observe(this) {
            Log.e("TAG", "DFragment观察到的数据:${it}")
        }

    }
}