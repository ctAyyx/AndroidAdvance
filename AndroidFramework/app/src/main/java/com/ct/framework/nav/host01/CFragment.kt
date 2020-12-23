package com.ct.framework.nav.host01

import android.util.Log
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ct.framework.R

class CFragment : BaseFragment() {
    override fun initBtn01Text() = "导航到DFragment"

    override fun onBtn01Click(view: View) {
        view.findNavController()
            .navigate(R.id.action_CFragment_to_DFragment)
    }

    override fun initView(root: View) {
        val previousEntry = findNavController().previousBackStackEntry
        val handle = previousEntry?.savedStateHandle ?: return

        handle.getLiveData<String>("").observe(this) {
            Log.e("TAG", "CFragment观察到的数据:${it}")
        }

        val previousEntry1 = findNavController().currentBackStackEntry
        val handle1 = previousEntry1?.savedStateHandle ?: return

        handle1.getLiveData<String>("").observe(this) {
            Log.e("TAG", "CFragment观察到的数据:${it}")
        }

    }
}