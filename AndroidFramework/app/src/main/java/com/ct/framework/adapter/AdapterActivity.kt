package com.ct.framework.adapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.observe
import androidx.paging.DataSource
import androidx.paging.toLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct.framework.BR
import com.ct.framework.R
import com.ct.framework.adapter.base.BaseMulPagedListAdapter
import com.ct.framework.adapter.base.BaseMulTypeListAdapter
import com.ct.framework.adapter.base.BasePagedListAdapter
import com.ct.framework.adapter.base.DataBoundListAdapter
import com.ct.framework.adapter.datasource.toPagedLiveData
import com.ct.framework.databinding.ItemSimpleAdapterBinding
import kotlinx.android.synthetic.main.activity_adapter.*

class AdapterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adapter)
        subscribeUi03()
    }

    private fun subscribeUi() {

        //val adapter = createSimpleAdapter()
        val adapter = createMulAdapter()
        rv_adapter.layoutManager = LinearLayoutManager(this)
        rv_adapter.adapter = adapter

        val mList = mutableListOf<AdapterVo>()
        for (i in 0..30) {
            mList.add(AdapterVo("测试用例:$i", i))
        }

        adapter.submitList(
            mList
        )

    }

    private fun subscribeUi02() {

        //val adapter = createSimpleAdapter()
        val adapter = createMulAdapter02()
        rv_adapter.layoutManager = LinearLayoutManager(this)
        rv_adapter.adapter = adapter

        val mList = mutableListOf<Any>()
        for (i in 0..30) {
            if (i % 4 == 0)
                mList.add(MulAdapterVo("多种数据", i + 100))
            else
                mList.add(AdapterVo("测试用例:$i", i))
        }

        adapter.submitList(
            mList
        )

    }

    private fun createSimpleAdapter() =
        object : DataBoundListAdapter<AdapterVo, ItemSimpleAdapterBinding>() {
            override fun getLayoutId(viewType: Int) = R.layout.item_simple_adapter

            override fun bind(binding: ItemSimpleAdapterBinding, item: AdapterVo, position: Int) {
                val newBinding: ViewDataBinding = binding
                newBinding.setVariable(BR.model, item)
                newBinding.setVariable(BR.onItemClick, createOnItemClickListener())
            }
        }

    private fun createMulAdapter() = object : BaseMulTypeListAdapter<AdapterVo>() {
        override fun getMulTypeLayoutId(position: Int): Int {
            val model = getItem(position)
            if (model.age % 5 == 0)
                return R.layout.item_mul_adapter_01
            return R.layout.item_simple_adapter
        }

        override fun bind(binding: ViewDataBinding, item: AdapterVo, position: Int) {
            binding.setVariable(BR.model, item)
            binding.setVariable(BR.onItemClick, createOnItemClickListener())
        }

    }


    private fun createOnItemClickListener() = View.OnClickListener {
        Log.e("TAG", "我被点击了:$it")
    }

    private fun createMulAdapter02() = object : BaseMulTypeListAdapter<Any>() {
        override fun getMulTypeLayoutId(position: Int): Int {
            val model = getItem(position)
            return when (model) {
                is MulAdapterVo -> R.layout.item_mul_adapter_02
                else -> {
                    if (position % 5 == 0)
                        R.layout.item_mul_adapter_01
                    else
                        R.layout.item_simple_adapter
                }
            }
        }

        override fun bind(binding: ViewDataBinding, item: Any, position: Int) {
            binding.setVariable(BR.model, item)
            binding.setVariable(BR.onItemClick, createOnItemClickListener())
        }

    }


    //================== Paging ==========================

    private fun subscribeUi03() {
        //val adapter = createPagingSimple()
        val adapter = createMulPaging()
        val factory = PagingSimpleDatasourceFactory()
        factory.toPagedLiveData(10, 1)
            .observe(this) {
                adapter.submitList(it)
            }

        rv_adapter.layoutManager = LinearLayoutManager(this)
        rv_adapter.adapter = adapter

    }

    private fun createPagingSimple() =
        object : BasePagedListAdapter<AdapterVo, ItemSimpleAdapterBinding>() {
            override fun getLayoutId(viewType: Int): Int {
                return R.layout.item_simple_adapter
            }

            override fun bind(binding: ItemSimpleAdapterBinding, item: AdapterVo?) {
                val newBinding: ViewDataBinding = binding
                newBinding.setVariable(BR.model, item)
                newBinding.setVariable(BR.onItemClick, createOnItemClickListener())
            }

        }


    private fun createMulPaging() = object : BaseMulPagedListAdapter<AdapterVo>() {
        override fun getMulTypeLayoutId(position: Int, model: AdapterVo?): Int {
            return model?.let {
                if (it.age % 4 == 0)
                    R.layout.item_mul_adapter_01
                else
                    R.layout.item_simple_adapter
            } ?: R.layout.item_simple_adapter
        }

        override fun bind(binding: ViewDataBinding, item: AdapterVo?) {
            binding.setVariable(BR.model, item)
            binding.setVariable(BR.onItemClick, createOnItemClickListener())
        }


    }

}