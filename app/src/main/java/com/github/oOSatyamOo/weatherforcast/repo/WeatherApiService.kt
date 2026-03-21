package com.github.oOSatyamOo.weatherforcast.repo

import com.github.oOSatyamOo.weatherforcast.data.remote.ForecastResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appId") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("cnt") cnt: Int = 24
    ): ForecastResponse
}