package com.ct.plugin.course01

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import com.ct.plugin.R
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.io.File
import java.util.*

/**
 *  插件化 第一节
 *
 *  加载插件apk中的普通类
 *
 *  反射 和 ClassLoader
 *
 *  在 Android10 以上使用有问题
 *  因为Android10 存储策略的改变
 *
 *
 * */
class Course01Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course01)
        printClassLoader()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 100
            )
        }
    }


    /**
     * 打印 当前的类加载器
     *
     * BootClassLoader : 用于SDK里面的类
     *
     * PathClassLoader : 用于加载应用的类
     *
     * DexClassLoader  : 用于开发人员加载指定Dex 或 Apk里面的类
     *
     * PathClassLoader 和 DexClassLoader在8.0以后没有什么区别了
     *
     * */
    private fun printClassLoader() {

        var classLoader = classLoader
        while (classLoader != null) {
            Log.e("TAG", "当前的类加载器:$classLoader")
            classLoader = classLoader.parent
        }
    }


    private fun loadDex() {

        // 创建一个类加载器

        /**
         * dexPath : 需要记在的dex apk的路径
         * optimizedDirectory: 优化目录
         *
         * parent:委托的ClassLoader
         * */

        //这里我们有一个 Demo.dex 文件 在SDK下
        //为了适配Android10 我们将文件放置在 应用私有外部目录下
        val path = PluginUtil.getPluginPath(this, "Demo.dex")

        val dexLoader =
            DexClassLoader(
                path,
                cacheDir.absolutePath,
                null,
                this.javaClass.classLoader
            )

        val cls = dexLoader.loadClass("com.ct.plugins.Demo")
        val method = cls.getDeclaredMethod("printName")
        method.isAccessible = true
        method.invoke(null)

        val obj = cls.newInstance()

        val method2 = cls.getDeclaredMethod("getName")
        method2.isAccessible = true
        val name = method2.invoke(obj)
        Log.e("TAG", "获取的名称是:$name")


    }


    /**
     * 加载 Apk中的普通类
     * */
    private fun loadApk() {

        //通过 源码知道
        //在ClassLoader中有一个 DexPathList 字段
        //在DexPathList中有一个 Element 数组
        //在Element    中有一个 DexFile 字段
        //就是通过DexFile来加载类的

        //步骤
        //获取应用的 Element数组
        //获取Apk中的Element数组
        //合并成一个新的Element数组 并赋值给应用的Element


        //1.获取Class
        val baseDexCls = Class.forName("dalvik.system.BaseDexClassLoader")
        val dexPathListCls = Class.forName("dalvik.system.DexPathList")

        //获取应用的Element数组
        val pathListField = baseDexCls.getDeclaredField("pathList")
        pathListField.isAccessible = true
        val elementsField = dexPathListCls.getDeclaredField("dexElements")
        elementsField.isAccessible = true

        //获取DexPathList
        val appPathList = pathListField.get(classLoader)

        //获取Elements数组
        val appElement: Array<Any> = elementsField.get(appPathList) as Array<Any>


        //开始加载Apk的普通类
        val apkPath = PluginUtil.getPluginPath(this, "plugins-debug.apk")
        val apkLoader = DexClassLoader(apkPath, cacheDir.absolutePath, null, classLoader)

        val apkPathListField = pathListField.get(apkLoader)
        val apkElement: Array<Any> = elementsField.get(apkPathListField) as Array<Any>

        //开始合并数组
        Log.e("TAG", "App Elements:${appElement}")
        Log.e("TAG", "Apk Elements:${apkElement}")
        Log.e("TAG", "获取Element:${appElement.javaClass.componentType}")

        val newElement = java.lang.reflect.Array.newInstance(
            appElement.javaClass.componentType,
            appElement.size + apkElement.size
        )

        System.arraycopy(appElement, 0, newElement, 0, appElement.size)
        System.arraycopy(apkElement, 0, newElement, appElement.size, apkElement.size)
        //开始给应用类加载器重新赋值
        elementsField.set(appPathList, newElement)


        //验证  这里我们加载 Demo类
        val cls = Class.forName("com.ct.plugins.Demo")
        Log.e("TAg", "验证:$cls")

        //开始创建对象
        val obj = cls.newInstance()

        val method1 = cls.getDeclaredMethod("printName")
        method1.isAccessible = true
        val method2 = cls.getDeclaredMethod("getName")
        method2.isAccessible = true

        //方法为静态方法
        method1.invoke(null)
        //方法为普通方法
        val name = method2.invoke(obj)
        Log.e("TAG", "获取的名称：$name")


    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btn_course01_01 -> loadDex()
            R.id.btn_course01_02 -> loadApk()
        }

    }


}