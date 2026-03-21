package com.github.oOSatyamOo.weatherforcast.data.remote

data class ForecastResponse(
    val list: List<ForecastItem>,
    val city: City
)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<WeatherCondition>
)

data class Main(
    val temp: Double,
    val temp_min: Double,
    val temp_max: Double
)

data class WeatherCondition(
    val description: String,
    val icon: String
)

data class City(val name: String)