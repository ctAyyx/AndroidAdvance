package com.ct.plugin.course01

import android.content.Context
import android.os.Environment
import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File

object PluginUtil {


    fun getPluginPath(context: Context, fileName: String): String {

        val pluginDir =
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.absolutePath ?: ""
        return File(pluginDir, fileName).absolutePath
    }

    fun loadApk(context: Context, apkPath: String) {
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
        val appPathList = pathListField.get(context.classLoader)

        //获取Elements数组
        val appElement: Array<Any> = elementsField.get(appPathList) as Array<Any>


        //开始加载Apk的普通类
        val apkLoader =
            DexClassLoader(apkPath, context.cacheDir.absolutePath, null, context.classLoader)

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
    }

}