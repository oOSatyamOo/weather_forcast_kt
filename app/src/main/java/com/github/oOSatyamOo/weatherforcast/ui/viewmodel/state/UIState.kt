package com.github.oOSatyamOo.weatherforcast.ui.viewmodel.state

import com.github.oOSatyamOo.weatherforcast.data.local.DailyForecast

sealed class UiState {
    object Loading : UiState()
    data class Success(val forecast: List<DailyForecast>) : UiState()
    data class Error(val message: String) : UiState()
}