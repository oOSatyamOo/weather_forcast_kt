package com.github.oOSatyamOo.weatherforcast.data.local

data class DailyForecast(
    val date: String,          // e.g. "Monday, Mar 23"
    val tempMin: Double,
    val tempMax: Double,
    val condition: String,
    val icon: String
)