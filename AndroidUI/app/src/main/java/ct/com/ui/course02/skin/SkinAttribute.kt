package ct.com.ui.course02.skin

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * 记录 所有需要换肤的View 和 属性
 * */
class SkinAttribute : SkinFactory2.SkinFactory2Interceptor {

    private val mAttributes = arrayListOf(
        "background",
        "src",
        "textColor"
    )

    //用来记录所有支持换肤的View
    private val skinViewList = mutableListOf<SkinView>()


    override fun interceptor(
        parent: View?,
        view: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {

        if (view != null) {
            val skinPairList = saveSkinViewAttrs(view, attrs)
            if (!skinPairList.isNullOrEmpty())
                skinViewList.add(SkinView(view, skinPairList))
        }

        return view
    }


    /**
     * 使用皮肤包资源
     * */
    fun applySkinSource() {

        skinViewList.forEach {
            it.applySkin()
        }
    }

    /**
     * 保存一个View 支持换肤的属性
     * */
    private fun saveSkinViewAttrs(view: View, attrs: AttributeSet): List<SkinPair> {
        val attrList = mutableListOf<SkinPair>()

        for (i in 0 until attrs.attributeCount) {


            val attrName = attrs.getAttributeName(i)

//            if (attrName == "theme") {
//                Log.e("TAG", "属性名称:$attrName")
//                val resNameID = attrs.getAttributeValue(i).substring(1).toInt()
//                val themeID=SkinThemeResource.getResId(view.context, intArrayOf(resNameID))[0]
//                Log.e("TAG","theme:${attrs.getAttributeValue(i)} -- ${themeID}")
//                val theme=SkinResource.getTheme(themeID)
//            }
            if (!mAttributes.contains(attrName))
                continue

            val attrValue = attrs.getAttributeValue(i)

            //Android中 以@开头的属性值 是引用系统资源 @后面为系统资源ID
            //          以?开头的属性值 是引用Theme中的资源 ?后面为Theme中这个资源名称的ID
            //          以#开头的属性值 固定属性值

            val resID = if (attrValue.startsWith("@")) {
                //获取到的是 自定义的ID
                attrValue.substring(1).toInt()

            } else if (attrValue.startsWith("?")) {
                //系统定义的ID 需要
                val resNameID = attrValue.substring(1).toInt()
                SkinThemeResource.getResId(view.context, intArrayOf(resNameID))[0]
            } else
                continue
            val pair = SkinPair(attrName, resID)
            attrList.add(pair)

        }


        return attrList
    }


    //用来记录 一个View需要修改的属性集合
    data class SkinView(val view: View, val pairs: List<SkinPair>) {

        //应用换肤
        fun applySkin() {
            pairs.forEach {
                when (it.attributeName) {
                    "background" -> applyBackground(view, it.resID)
                    "src" -> applySrc(view, it.resID)
                    "textColor" -> applyTextColor(view, it.resID)
                }

            }

        }

        /**
         * 应用背景皮肤
         * */
        private fun applyBackground(view: View, resID: Int) {
            val backgroundResource = SkinResource.getBackground(resID)
            if (backgroundResource is Drawable)
                view.background = backgroundResource
            else if (backgroundResource is Int)
                view.setBackgroundColor(backgroundResource)
        }

        /**
         * 应用资源皮肤
         * */
        private fun applySrc(view: View, resID: Int) {
            val srcResource = SkinResource.getBackground(resID)
            if (srcResource is Drawable && view is ImageView)
                view.setImageDrawable(srcResource)
            else if (srcResource is Int && view is ImageView)
                view.setImageDrawable(ColorDrawable(srcResource))
        }

        /**
         * 应用文本颜色皮肤
         * */
        private fun applyTextColor(view: View, resID: Int) {
            val textColorResource = SkinResource.getColor(resID)
            if (view is TextView)
                view.setTextColor(textColorResource)
        }


    }


    //用来记录 需要修改的属性名称 和 对应的ID
    //通过 resID -获取->resName,resType -获取皮肤包对应的resID-> skinResource获取value
    data class SkinPair(val attributeName: String, val resID: Int)

}