package com.github.oOSatyamOo.weatherforcast.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun listToJson(value: List<DailyForecast>): String = gson.toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<DailyForecast> {
        val type = object : TypeToken<List<DailyForecast>>() {}.type
        return gson.fromJson(value, type)
    }
}