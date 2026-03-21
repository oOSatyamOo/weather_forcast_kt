package com.github.oOSatyamOo.weatherforcast.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "weather_forecast")
@TypeConverters(Converters::class)
data class WeatherForecastEntity(
    @PrimaryKey val cityName: String,
    val lastUpdated: Long = System.currentTimeMillis(),
    val dailyForecasts: List<DailyForecast>
)