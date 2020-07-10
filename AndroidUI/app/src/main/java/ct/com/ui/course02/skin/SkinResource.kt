package ct.com.ui.course02.skin

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.IdRes

/**
 * 加载指定皮肤包下面的资源
 * */
object SkinResource {

    private lateinit var mContext: Context
    private lateinit var appSource: Resources
    private lateinit var skinSource: Resources
    private var skinPackageName: String = ""


    /**
     * 初始化皮肤包资源类
     * @param context
     * @param skinPath 皮肤包路径
     * */
    fun init(context: Context, skinPath: String) {
        mContext = context.applicationContext
        val packageManager = mContext.packageManager
        val info = packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
        skinPackageName = info?.packageName ?: ""
        appSource = mContext.resources

        initSkinSource(skinPath)
    }

    private fun initSkinSource(path: String) {

        val assetManager = AssetManager::class.java.getDeclaredConstructor().newInstance()
        val method = assetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
        method.isAccessible = true
        method.invoke(assetManager, path)
        skinSource = Resources(assetManager, appSource.displayMetrics, appSource.configuration)

        Log.e("TAG", "${mContext.theme}创建的皮肤包Theme：${skinSource.newTheme()}")
    }

    fun getSkinTheme(): Resources.Theme = skinSource.newTheme()

    private fun getSkinResourceId(id: Int): Int? {

        //获取宿主APP资源名称 类型
        val entryName = appSource.getResourceEntryName(id)
        val typeName = appSource.getResourceTypeName(id)

        //获取资源包包中的资源ID
        return skinSource.getIdentifier(entryName, typeName, skinPackageName)
    }


    /**
     *
     * 获取皮肤包 Color属性
     * @param colorId color的id
     * */
    fun getColor(colorId: Int): Int {
        val skinID = getSkinResourceId(colorId)
        if (skinID == null || skinID == 0)
            return appSource.getColor(colorId)
        return skinSource.getColor(skinID)
    }


    fun getDrawable(drawableId: Int): Drawable {
        val skinID = getSkinResourceId(drawableId)
        if (skinID == null || skinID == 0)
            return appSource.getDrawable(drawableId)
        return skinSource.getDrawable(skinID)
    }

    fun getString(strId: Int): String {
        val skinID = getSkinResourceId(strId)
        if (skinID == null || skinID == 0)
            return appSource.getString(strId)
        return skinSource.getString(skinID)
    }

    fun getBackground(backgroundId: Int): Any {
        val resourceType = appSource.getResourceTypeName(backgroundId)
        if ("color" == resourceType)
            return getColor(backgroundId)
        return getDrawable(backgroundId)
    }


    fun getTheme(themeId: Int): Resources.Theme {

        val themeType = appSource.getResourceTypeName(themeId)
        val themeName = appSource.getResourceEntryName(themeId)
        val id = skinSource.getIdentifier(themeName, themeType, skinPackageName)
        Log.e("TAG", "主题:${themeName} -- ${themeType} -- ${id}")
        val theme = skinSource.newTheme().apply { applyStyle(id, true) }
        Log.e("TAG","皮肤包:${skinSource.getResourceTypeName(id)
                } -- ${skinSource.getResourceEntryName(id)}")

        return theme


    }
}