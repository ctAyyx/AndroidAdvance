package ct.com.ui.course02.skin

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import java.lang.reflect.Constructor
import java.util.*

class SkinFactory2 : LayoutInflater.Factory2 {

    private val sClassPrefixList = arrayOf(
        "android.widget.",
        "android.webkit.",
        "android.app.",
        "android.view."
    )

    private val sConstructorMap =
        HashMap<String, Constructor<out View?>>()

    private val mConstructorSignature = arrayOf(
        Context::class.java, AttributeSet::class.java
    )

    val skinAttribute = SkinAttribute()

    private var interceptor: SkinFactory2Interceptor? = null

    fun setInterceptor(interceptor: SkinFactory2Interceptor) {
        this.interceptor = interceptor
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {



        var view = createSDKView(name, context, attrs)
        if (view == null)
            view = createView(name, context, attrs)

        //在这里统计需要换肤的控件 和 属性
//        return interceptor?.interceptor(parent, view, name, context, attrs) ?: view

        return skinAttribute.interceptor(parent, view, name, context, attrs)

    }


    /**
     * 创建原生SDK控件
     * */
    private fun createSDKView(name: String, context: Context, attrs: AttributeSet): View? {
        //不是原生控件
        if (-1 != name.indexOf('.')) {
            return null
        }
        sClassPrefixList.forEach {
            val view = createView(it + name, context, attrs)
            if (view != null)
                return view
        }

        return null

    }

    /**
     * 创建控件
     *
     * @param name 控件的全名
     * @param mContext
     * @param attrs
     * */
    private fun createView(name: String, mContext: Context, attrs: AttributeSet): View? {

        val constructor = findConstructor(name, mContext)
        try {
            return constructor?.newInstance(mContext, attrs)
        } catch (e: Exception) {
        }
        return null

    }

    /**
     * 获取构造函数
     * */
    private fun findConstructor(
        name: String,
        mContext: Context
    ): Constructor<out View>? {
        var constructor: Constructor<out View?>? =
            sConstructorMap[name]
        if (constructor == null) {

            try {
                val clazz1 = Class.forName(
                    name, false,
                    mContext.classLoader
                ).asSubclass(View::class.java)

                constructor = clazz1.getConstructor(*mConstructorSignature)
                constructor.isAccessible = true
                sConstructorMap[name] = constructor
            } catch (e: Exception) {

            }

        }
        return constructor
    }


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? = null


    interface SkinFactory2Interceptor {

        fun interceptor(
            parent: View?,
            view: View?,
            name: String,
            context: Context,
            attrs: AttributeSet
        ): View?
    }
}