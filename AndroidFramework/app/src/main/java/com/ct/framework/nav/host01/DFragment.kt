package com.ct.framework.nav.host01

import android.util.Log
import android.view.View
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ct.framework.R

class DFragment : BaseFragment() {
    override fun initBtn01Text() = "导航到BFragment"

    override fun onBtn01Click(view: View) {
        view.findNavController()
            .navigate(R.id.action_DFragment_to_BFragment)

//
//        view.findNavController()
//            .popBackStack(R.id.BFragment, true)

    }

    override fun initView(root: View) {
        val previousEntry = findNavController().previousBackStackEntry
        val handle = previousEntry?.savedStateHandle ?: return

        handle.getLiveData<String>("").observe(this) {
            Log.e("TAG", "DFragment观察到的数据:${it}")
        }

    }


}