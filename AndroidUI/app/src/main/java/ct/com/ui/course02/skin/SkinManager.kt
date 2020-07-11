package ct.com.ui.course02.skin

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import java.lang.NullPointerException

/**
 * 皮肤包管理器
 * */

class SkinManager private constructor() {
    private val mFactories = mutableMapOf<Activity, SkinFactory2>()

    companion object {
        private lateinit var mContext: Context
        fun init(context: Context) {
            mContext = context.applicationContext
        }

        val INSTANCE: SkinManager by lazy {
            //if (Companion::mContext.isInitialized)
            SkinManager()
            //throw NullPointerException("you  should use init(Context) before")
        }
    }


    /**
     * 由我们定义的工厂来接管系统XML布局的创建过程
     * @param activity
     * */
    fun registerSkinFactory(activity: Activity) {

        try {
            //获取Activity的 PhoneLayoutInflater
            val inflater = activity.layoutInflater

            //利用反射将 mFactorySet 字段设置为false
            //表示尚未设置工厂
            val filed = LayoutInflater::class.java.getDeclaredField("mFactorySet")
            filed.isAccessible = true
            filed.set(inflater, false)
            inflater.factory2 = SkinFactory2().apply {
                mFactories[activity] = this
            }

        } catch (e: Exception) {
        }

    }

    /**
     * 加载皮肤包
     * @param skinPath
     *
     * */
    fun loadSkin(skinPath: String) {

        if (skinPath.isBlank()) {

            return
        }

        SkinResource.init(mContext, skinPath)

    }

    fun applySkin(activity: Activity) {
        mFactories[activity]
            ?.skinAttribute
            ?.applySkinSource()

    }

    fun unRegisterSkinFactory(activity: Activity) {
        mFactories.remove(activity)
    }

}