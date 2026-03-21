package com.github.oOSatyamOo.weatherforcast.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: WeatherForecastEntity)

    @Query("SELECT * FROM weather_forecast WHERE cityName = :city LIMIT 1")
    suspend fun getForecast(city: String): WeatherForecastEntity?
}