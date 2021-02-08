package com.ct.framework.nav.host01

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.ct.framework.R

class AFragment : BaseFragment() {

    override fun initBtn01Text() = "导航到BFragment"
    override fun onBtn01Click(view: View) {
//        view.findNavController()
//            .navigate(R.id.action_AFragment_to_BFragment)

        val a = AFragmentDirections.actionAFragmentToBFragment("XXX")
        findNavController(this).navigate(a)

//        val request = NavDeepLinkRequest.Builder
//            .fromUri("android-app://androidx.navigation.app/profile".toUri())
//            .build()
//        findNavController(this).navigate(request)

    }

    override fun initView(root: View) {
        val btn02 = root.findViewById<AppCompatButton>(R.id.btn_navHost02)
        btn02.text = "深度链接"
        val pendingIntent = NavDeepLinkBuilder(requireContext())
            .setGraph(R.navigation.nav_host_01)
            .setDestination(R.id.CFragment)
            .createPendingIntent()
        btn02.setOnClickListener {

        }


    }

}