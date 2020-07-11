package ct.com.ui.course02.skin

import android.app.Activity
import android.content.Context
import android.os.Build
import ct.com.ui.R

/**
 * Theme 资源
 *
 *
 * */
object SkinThemeResource {

    private val primaryDark = intArrayOf(
        androidx.appcompat.R.attr.colorPrimaryDark
    )

    private val systemBar = intArrayOf(
        android.R.attr.statusBarColor,
        android.R.attr.navigationBarColor
    )

    /**
     *@param attrs Theme中资源名称ID 也就是?后面的值
     *
     * */
    fun getResId(context: Context, attrs: IntArray): IntArray {

        val redIDs = IntArray(attrs.size)
        val a = context.obtainStyledAttributes(attrs)

        attrs.forEachIndexed { index, _ ->
            redIDs[index] = a.getResourceId(index, 0)
        }
        a.recycle()

        return redIDs
    }


    fun applyThemeSkin(activity: Activity) {

        //小于5.0不能修改
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            return

        val resIDs = getResId(activity, systemBar)
        val statusBarColorResId = resIDs[0]
        val navigationBarColorResId = resIDs[1]

        if (statusBarColorResId != 0) {
            val color = SkinResource.getColor(statusBarColorResId)
            activity.window.statusBarColor = color
        } else {
            val colorResId = getResId(activity, primaryDark)[0]
            if (colorResId != 0) {
                val color = SkinResource.getColor(colorResId)
                activity.window.statusBarColor = color
            }
        }


    }

}