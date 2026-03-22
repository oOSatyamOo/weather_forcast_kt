package com.github.oOSatyamOo.weatherforcast.repo.usecase

import com.github.oOSatyamOo.weatherforcast.data.local.DailyForecast
import com.github.oOSatyamOo.weatherforcast.repo.WeatherRepo
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val repository: WeatherRepo
) {
    suspend operator fun invoke(city: String): List<DailyForecast> {
        return repository.getForecast(city)
    }
}