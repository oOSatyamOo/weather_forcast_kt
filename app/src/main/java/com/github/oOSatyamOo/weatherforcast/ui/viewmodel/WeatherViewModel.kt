package com.github.oOSatyamOo.weatherforcast.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.oOSatyamOo.weatherforcast.repo.usecase.GetWeatherForecastUseCase
import com.github.oOSatyamOo.weatherforcast.ui.viewmodel.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherForecastUseCase
) : ViewModel() {

    var uiState by mutableStateOf<UiState>(UiState.Loading)
        private set

    init {
        fetchWeather("Bangalore")
    }
    fun fetchWeather(city: String) {

        if (city.isBlank()) {
            uiState = UiState.Error("Please enter a city name")
            return
        }

        viewModelScope.launch {
            uiState = UiState.Loading
            try {
                val forecast = getWeatherUseCase(city.trim())
                uiState = UiState.Success(forecast)
            } catch (e: Exception) {
                uiState = UiState.Error(
                    e.message ?: "Failed to fetch weather. Please check your internet or try again."
                )
            }
        }
    }
    private var searchJob: Job? = null

    fun onCityChanged(city: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(1000)

            if (city.isNotBlank()) {
                fetchWeather(city.trim())
            }
        }
    }
}
