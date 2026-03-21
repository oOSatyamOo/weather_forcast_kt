package com.github.oOSatyamOo.weatherforcast.repo


import com.github.oOSatyamOo.weatherforcast.data.local.DailyForecast

import retrofit2.http.GET

interface WeatherRepo {
    @GET("forecast")
    suspend fun getForecast(city: String): List<DailyForecast>
}
