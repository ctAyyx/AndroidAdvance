package com.ct.framework.jetpack

import android.content.Context

/**
 * DataStore
 *
 *
 * */
object DataStoreHelper {

    private lateinit var app: Context

    fun init(context: Context) {
        this.app = context.applicationContext
    }



}