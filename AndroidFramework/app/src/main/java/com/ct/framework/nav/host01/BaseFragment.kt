package com.ct.framework.nav.host01

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.ct.framework.R

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TAG", "onCreate:${this::class.java.simpleName}")
    }

//    override fun onPause() {
//        super.onPause()
//        Log.e("TAG", "onPause:${this::class.java.simpleName}")
//    }
//
//    override fun onStop() {
//        super.onStop()
//        Log.e("TAG", "onStop:${this::class.java.simpleName}")
//    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("TAG", "onDestroy:${this::class.java.simpleName}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("TAG", "onDetach:${this::class.java.simpleName}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("TAG", "onDestroyView:${this::class.java.simpleName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", "onCreateView:${this::class.java.simpleName} $this")
        val rootView = inflater.inflate(R.layout.fragment_host_01, container, false)
        val btn01 = rootView.findViewById<AppCompatButton>(R.id.btn_navHost01)
        btn01.text = initBtn01Text()
        btn01.setOnClickListener {
            onBtn01Click(it)
        }
        initView(rootView)
        return rootView
    }

    abstract fun initBtn01Text(): String

    abstract fun onBtn01Click(view: View)

    open fun initView(root: View) {}


}