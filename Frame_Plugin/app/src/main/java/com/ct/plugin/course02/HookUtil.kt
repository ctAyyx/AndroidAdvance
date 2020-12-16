package com.ct.plugin.course02

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.ArrayMap
import android.util.Log
import java.lang.reflect.Field
import java.lang.reflect.Proxy

private const val TARGET_INTENT = "target_intent"
const val FLAG_PLUGIN = "IsPlugin"

object HookUtil {


    /**
     *
     * 这里主要Hook IActivityManager这个接口 使用动态代理
     *
     * 主要思路 替换Singleton 里面的 mInstance
     *
     * */
    fun hookAMS() {
        val singletonField = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                //获取 IActivityTaskManagerSingleton
                val activityManagerCls = Class.forName("android.app.ActivityTaskManager")
                activityManagerCls.getDeclaredField("IActivityTaskManagerSingleton")
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                val activityManagerCls = Class.forName("android.app.ActivityManager")
                activityManagerCls.getDeclaredField("IActivityManagerSingleton")
            }
            else -> {
                val activityManagerCls = Class.forName("android.app.ActivityManagerNative")
                activityManagerCls.getDeclaredField("gDefault")
            }
        }

        singletonField.isAccessible = true
        val singletonObj = singletonField.get(null)
        //获取Singleton 里面的mInstance
        val singletonCls = Class.forName("android.util.Singleton")
        val mInstanceField = singletonCls.getDeclaredField("mInstance")
        mInstanceField.isAccessible = true
        val oldInstanceObj = mInstanceField.get(singletonObj)


        //开始 使用动态代理
        val mProxyInterface = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                Class.forName("android.app.IActivityTaskManager")
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                Class.forName("android.app.IActivityManager")
            }
            else -> {
                Class.forName("android.app.IActivityManager")
            }
        }
        val mInstanceProxy = Proxy.newProxyInstance(
            this.javaClass.classLoader,
            arrayOf(mProxyInterface)
        ) { _, method, args ->

            if ("startActivity" == method.name) {
                //这里我们开始替换 传递过来的Intent

                args?.forEachIndexed { index, data ->
                    if (data is Intent) {
                        Log.e("TAG", "获取到了Intent:${data.component?.className}")
                        val isOpenPlugins = data.getBooleanExtra(FLAG_PLUGIN, false)
                        if (isOpenPlugins) {

                            val intent = Intent()
                            intent.setClassName(
                                "com.ct.plugin",
                                "com.ct.plugin.course02.PluginActivity"
                            )
                            intent.putExtra(TARGET_INTENT, data)

                            args[index] = intent
                        }
                    }
                }
            }

            if (args == null)
                method.invoke(oldInstanceObj)
            else
                method.invoke(oldInstanceObj, *args)
        }

        Log.e(
            "TAG",
            "被代理的接口:${oldInstanceObj?.javaClass?.name}  --- 代理接口:${mProxyInterface.name} -- 代理类:${mInstanceProxy.javaClass.name}"
        )
        //替换
        mInstanceField.set(singletonObj, mInstanceProxy)
    }


    /**
     *  Hook ActivityThread 中的H
     * */

    fun hookHandler() {

        //获取 H
        val threadCls = Class.forName("android.app.ActivityThread")
        val threadField = threadCls.getDeclaredField("sCurrentActivityThread")
        threadField.isAccessible = true
        val threadObj = threadField.get(null)

        val mHField = threadCls.getDeclaredField("mH")
        mHField.isAccessible = true
        val mHObj = mHField.get(threadObj)

        //创建一个Handler的Callback
        val launcherActivityCallback = Handler.Callback { msg ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && msg.what == 159) {

                // TransactionExecutor 源码中
                // final IBinder token = transaction.getActivityToken();
                // ActivityClientRecord r = mTransactionHandler.getActivityClient(token);

                //这里的 mTransactionHandler 就是 ActivityThread
                //这里我们反射修改 ActivityClientRecord 里面的Intent

//                val clientTransactionObj = msg.obj
//                val tokenMethod =
//                    clientTransactionObj.javaClass.getDeclaredMethod("getActivityToken")
//                tokenMethod.isAccessible = true
//                val tokenObj = tokenMethod.invoke(clientTransactionObj)
//
//                Log.e("TAg", "Token:$tokenObj")
//                val getClientMethod =
//                    threadCls.getDeclaredField("mActivities")
//                getClientMethod.isAccessible = true
//                val map: ArrayMap<IBinder, Any> =
//                    getClientMethod.get(threadObj) as ArrayMap<IBinder, Any>
//                val recordObj = map[tokenObj as IBinder]
//                if (recordObj != null) {
//                    val intentField = recordObj.javaClass.getDeclaredField("intent")
//                    intentField.isAccessible = true
//                    val oldIntent = intentField.get(recordObj) as Intent
//                    val targetIntent = oldIntent.getParcelableExtra<Intent>(TARGET_INTENT)
//                    if (targetIntent != null) {
//                        //替换Intent
//                        intentField.set(recordObj, targetIntent)
//                    }
//                }

                try {
                    // 获取 mActivityCallbacks 对象
                    val mActivityCallbacksField: Field = msg.obj.javaClass
                        .getDeclaredField("mActivityCallbacks")
                    mActivityCallbacksField.isAccessible = true
                    val mActivityCallbacks: List<Any> =
                        mActivityCallbacksField.get(msg.obj) as List<Any>
                    mActivityCallbacks.forEachIndexed { i, _ ->
                        if (mActivityCallbacks[i].javaClass.name
                            == "android.app.servertransaction.LaunchActivityItem"
                        ) {
                            val launchActivityItem = mActivityCallbacks[i]!!

                            // 获取启动代理的 Intent
                            val mIntentField: Field = launchActivityItem.javaClass
                                .getDeclaredField("mIntent")
                            mIntentField.isAccessible = true

                            // 目标 intent 替换 proxyIntent
                            val proxyIntent: Intent = mIntentField.get(launchActivityItem) as Intent
                            val intent = proxyIntent.getParcelableExtra<Intent>(TARGET_INTENT)
                            if (intent != null) {
                                mIntentField.set(launchActivityItem, intent)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }


            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && msg.what == 100) {
                //在这里将数据Intent置换成插件的
                val recordObj = msg.obj
                val intentField = recordObj.javaClass.getDeclaredField("intent")
                intentField.isAccessible = true
                val oldIntent = intentField.get(recordObj) as Intent
                val targetIntent = oldIntent.getParcelableExtra<Intent>(TARGET_INTENT)
                if (targetIntent != null) {
                    //替换Intent
                    intentField.set(recordObj, targetIntent)
                }


            }


            false
        }

        //将回调注入H
        val handlerCls = Class.forName("android.os.Handler")
        val handlerCbField = handlerCls.getDeclaredField("mCallback")
        handlerCbField.isAccessible = true
        handlerCbField.set(mHObj, launcherActivityCallback)

    }

}