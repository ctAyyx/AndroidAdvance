package ct.com.ui.course02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import ct.com.ui.R


/**
 *
 * 对 LayoutInflater  inflater方法的使用
 * */
class LayoutInflaterActivity : AppCompatActivity() {

    private val mResourceId = R.layout.layout_use_inflater
    private lateinit var root: FrameLayout
    private lateinit var inflater: LayoutInflater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_inflater)
        root = findViewById(R.id.frame_root)
        inflater = LayoutInflater.from(this)


        Log.e("TAG", "===>${resources.assets}  ===${application.resources.assets} ")


    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_inflater_01 -> {
                useInflater01()
            }
            R.id.btn_inflater_02 -> {
                useInflater02()
            }
            R.id.btn_inflater_03 -> {
                useInflater03()
            }
        }
    }

    /**
     * 使用 inflater方法 参数 root==null
     *
     *  int resource 布局的ID
     *  @Nullable ViewGroup root 父控件
     *  boolean attachToRoot 判断是否将创建的视图添加到父控件 如果为 false 那么父控件只用于确定需要创建的视图对象的LayoutParams
     *                                                  true 表示要将创建的视图添加到父控件
     * */
    private fun useInflater01() {
        //这样传递参数 相当于  inflater.inflate(mResourceId, null, false)
        //这样创建的View 是没有布局参数的 LayoutParams
        val view = inflater.inflate(mResourceId, null)

        showLayoutParams(view)
        //这里添加子视图的时候 如果没有布局参数 ViewGroup创建默认的布局参数 宽高都是WRAP_CONTENT
        //这个时候 子布局的MeasureSpec的模式就是 AT_MOST

        //但是 这里我们使用的是FrameLayout 默认创建的布局参数  宽高都是MATCH_CONTENT
        //这个时候 子布局的MeasureSpec的模式就是 EXACTLY

        root.addView(view)
        showLayoutParams(view)
        Log.e("TAG", "$root++++生成的View:$view")
    }

    private fun useInflater02() {
        //这里返回的view 是布局的根布局
        val view = inflater.inflate(mResourceId, root, false)
        showLayoutParams(view)
        root.addView(view)
        Log.e("TAG", "$root++++生成的View:$view")
    }

    private fun useInflater03() {
        //这里返回的view 是我们传入的父布局
        val view = inflater.inflate(mResourceId, root, true)
        showLayoutParams(view)
        Log.e("TAG", "$root++++生成的View:$view")
    }


    private fun showLayoutParams(view: View) {
        val params = view.layoutParams
        if (params != null)
            Log.e("TAG", "$view 布局参数:${params.width} === ${params.height}")
        Log.e("TAG", "$view 布局宽高:${view.width} === ${view.height}")
    }

}