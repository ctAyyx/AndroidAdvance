package ct.com.ui.course09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ct.com.ui.R
import ct.com.ui.course09.adapter.CategoryRvAdapter
import ct.com.ui.course09.decoration.StickHeaderItemDecoration
import ct.com.ui.course09.vo.Category
import kotlin.concurrent.thread


/**
 *RecyclerView ItemDecoration 的使用
 * */
class Course09Activity : AppCompatActivity() {

    private lateinit var rv: RecyclerView
    private lateinit var adapter: CategoryRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course09)
        rv = findViewById(R.id.rv_course09)
        adapter = CategoryRvAdapter()
        useLinear()
        loadData()

    }

    private fun loadData() {
        val list = mutableListOf<Category>()
        for (i in 0..1000) {
            val model = Category(
                "测试数据$i",
                "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2220381114,787577306&fm=26&gp=0.jpg"
            )
            list.add(model)
        }

        adapter.submit(list)
    }

    private fun useLinear() {
        rv.layoutManager = LinearLayoutManager(this)
        // rv.layoutManager = GridLayoutManager(this, 2)
        rv.adapter = adapter
        rv.addItemDecoration(StickHeaderItemDecoration(adapter))
    }


}