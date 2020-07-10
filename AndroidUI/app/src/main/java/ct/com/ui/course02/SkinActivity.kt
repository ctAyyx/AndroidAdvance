package ct.com.ui.course02

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import ct.com.ui.R
import ct.com.ui.course02.skin.*
import ct.com.ui.course02.ui.FragmentVp
import kotlinx.android.synthetic.main.activity_skin.*
import java.io.File

private const val skinPath = "/storage/emulated/0/Android/data/ct.com.ui/files/app-debug.apk"

class SkinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //这两步操作可以放在Application中
        SkinManager.init(this)
        SkinManager.INSTANCE.loadSkin(skinPath)
        //替换Factory2
        SkinManager.INSTANCE.registerSkinFactory(this)


//        val filed=ContextThemeWrapper::class.java.getDeclaredField("mTheme")
//        filed.isAccessible =true
//        filed.set(this,)

//
//        try {
//
//            val field = ContextThemeWrapper::class.java.getDeclaredField("mThemeResource")
//            val field2 = ContextThemeWrapper::class.java.getDeclaredField("mTheme")
//            field.isAccessible = true
//            val id: Int = field.get(this) as Int
//
//            Log.e("TAG", "主题地址:${id}")
//
//            val skinTheme = SkinResource.getTheme(id)
//            theme.setTo(skinTheme)
//            // field2.isAccessible = true
//            // field2.set(this, theme)
//
//        } catch (e: Exception) {
//        }
reflect()
        //setTheme(R.style.Test)
        setContentView(R.layout.activity_skin)



        btn_skin.setOnClickListener {
            SkinManager.INSTANCE.applySkin(this)
            SkinThemeResource.applyThemeSkin(this)
        }



        vp_skin.adapter = FragmentVp(supportFragmentManager)
    }


    private fun reflect() {

        val mThemeImpl = Resources.Theme::class.java.getDeclaredField("mThemeImpl")
        val mThemeImplObj = mThemeImpl.get(theme)

        val mKey = mThemeImplObj::class.java.getDeclaredField("mKey")
        val mKeyObj = mKey.get(mThemeImplObj)
        val mResId = mKeyObj::class.java.getDeclaredField("mResId")
        val mResIdArray = mResId.get(mKeyObj) as IntArray
        Log.e("TAG","---->$mResIdArray")

    }


    override fun onDestroy() {
        super.onDestroy()
        SkinManager.INSTANCE.unRegisterSkinFactory(this)
    }

    private fun test() {


        with(resources) {
            Log.e(
                "TAG", "${getResourceEntryName(R.string.skin_title)
                } -- ${getResourceTypeName(R.string.skin_title)
                } -- ${getResourceName(R.string.skin_title)} ---- ${getResourcePackageName(R.string.skin_title)}"
            )


            Log.e(
                "TAG",
                "ID:${getIdentifier(
                    getResourceEntryName(R.string.skin_title),
                    getResourceTypeName(R.string.skin_title),
                    "ct.com.ui"
                )}" +
                        "${getString(
                            getIdentifier(
                                getResourceEntryName(R.string.skin_title),
                                getResourceTypeName(R.string.skin_title),
                                "ct.com.ui"
                            )
                        )}" +
                        ""
            )

        }
    }
}