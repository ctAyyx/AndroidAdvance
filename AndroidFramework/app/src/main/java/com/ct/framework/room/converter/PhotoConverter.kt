package com.ct.framework.room.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PhotoConverter {

    @TypeConverter
    fun fromJson(json: String): List<String> {
        return Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun toJson(photos: List<String>): String {
        return Gson().toJson(photos)
    }

    @TypeConverter
    fun fromJson2(json: String): List<Int> {
        return Gson().fromJson(json, object : TypeToken<List<Int>>() {}.type)
    }

    @TypeConverter
    fun toJson2(photos: List<Int>): String {
        return Gson().toJson(photos)
    }
}