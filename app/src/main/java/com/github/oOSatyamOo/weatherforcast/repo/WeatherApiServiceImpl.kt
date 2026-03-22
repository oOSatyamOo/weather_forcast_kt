package com.github.oOSatyamOo.weatherforcast.repo

import com.github.oOSatyamOo.weatherforcast.data.local.DailyForecast
import com.github.oOSatyamOo.weatherforcast.data.local.ForecastDao
import com.github.oOSatyamOo.weatherforcast.data.local.WeatherForecastEntity
import com.github.oOSatyamOo.weatherforcast.data.remote.ForecastResponse
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService,
    private val dao: ForecastDao,
    private val apiKey: String
) : WeatherRepo {
    override suspend fun getForecast(city: String): List<DailyForecast> {
        require(city.trim().isNotEmpty()) {
            "City name cannot be empty"
        }
        return try {

            val response = apiService.getForecast(city, apiKey)
            val dailyList = mapToDailyForecasts(response)

            // Save to Room
            val entity = WeatherForecastEntity(
                cityName = city.lowercase(),
                dailyForecasts = dailyList
            )
            dao.insertForecast(entity)

            dailyList
        } catch (e: Exception) {
            dao.getForecast(city.lowercase())?.dailyForecasts
                ?: throw Exception(e.message)
        }
    }

    private fun mapToDailyForecasts(response: ForecastResponse): List<DailyForecast> {

        val calendar = java.util.Calendar.getInstance()
        val formatter = java.text.SimpleDateFormat(
            "EEEE, MMM dd",
            java.util.Locale.getDefault()
        )

        return response.list
            .groupBy { item ->
                calendar.timeInMillis = item.dt * 1000

                // Create a stable key per day
                Pair(
                    calendar.get(java.util.Calendar.YEAR),
                    calendar.get(java.util.Calendar.DAY_OF_YEAR)
                )
            }
            .values
            .sortedBy { dayItems -> dayItems.first().dt } // ensure chronological order
            .take(3)
            .map { dayItems ->

                val first = dayItems.first()

                calendar.timeInMillis = first.dt * 1000
                val date = formatter.format(calendar.time)

                val weather = first.weather.firstOrNull()

                DailyForecast(
                    date = date,
                    tempMin = dayItems.minOf { it.main.temp_min },
                    tempMax = dayItems.maxOf { it.main.temp_max },
                    condition = weather?.description.orEmpty(),
                    icon = weather?.icon.orEmpty()
                )
            }
    }
}