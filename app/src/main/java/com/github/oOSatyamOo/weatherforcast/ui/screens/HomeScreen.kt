package com.github.oOSatyamOo.weatherforcast.ui.screens

import com.github.oOSatyamOo.weatherforcast.ui.viewmodel.WeatherViewModel


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.github.oOSatyamOo.weatherforcast.BuildConfig
import com.github.oOSatyamOo.weatherforcast.data.local.DailyForecast
import com.github.oOSatyamOo.weatherforcast.ui.components.PrimaryButton
import com.github.oOSatyamOo.weatherforcast.ui.components.PrimaryTextField
import com.github.oOSatyamOo.weatherforcast.ui.components.WeatherAppBar
import com.github.oOSatyamOo.weatherforcast.ui.theme.WeatherForcastTheme
import com.github.oOSatyamOo.weatherforcast.ui.viewmodel.state.UiState

@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    HomeScreenContent(
        uiState = viewModel.uiState,
        onCityChanged = viewModel::onCityChanged,
        onFetchClick = viewModel::fetchWeather
    )
}

@Composable
fun HomeScreenContent(
    uiState: UiState,
    onCityChanged: (String) -> Unit,
    onFetchClick: (String) -> Unit
) {
    var city by remember { mutableStateOf("Bengaluru") }   // Default city for you

    Scaffold(
        topBar = {
            WeatherAppBar(title = "Weather Forecast")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // City Search Row
            Row(

                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                PrimaryTextField(
                    value = city,
                    onValueChange = {
                        city = it
                        onCityChanged(it)
                    },
                    label = "Enter City Name"
                )

                Spacer(modifier = Modifier.width(8.dp))

                PrimaryButton(
                    "Fetch",
                    onClick = { onFetchClick(city) },)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // UI State Handling
            when (uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                    Text(
                        text = "Fetching weather...",
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                is UiState.Success -> {
                    val forecast = (uiState as UiState.Success).forecast
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(forecast) { day ->
                            WeatherDayCard(day)
                        }
                    }
                }

                is UiState.Error -> {
                    Text(
                        text = (uiState as UiState.Error).message,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PrimaryButton(
                        "Retry",
                        onClick = { onFetchClick(city) },)
                }
            }
        }
    }
}

@Composable
fun WeatherDayCard(day: DailyForecast) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Weather Icon
            AsyncImage(
                model = BuildConfig.OPENWEATHER_BASE_URL+"img/wn/${day.icon}@2x.png",
                contentDescription = day.condition,
                modifier = Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Weather Details
            Column {
                Text(
                    text = day.date,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${day.tempMax.toInt()}°C / ${day.tempMin.toInt()}°C",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    text = day.condition.replaceFirstChar { if (it.isLowerCase()) it.titlecase(java.util.Locale.ROOT) else it.toString() },   // ← Simple & clean way
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenLoadingPreview() {
    WeatherForcastTheme {
        HomeScreenContent(
            uiState = UiState.Loading,
            onCityChanged = {},
            onFetchClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenSuccessPreview() {
    val sampleForecast = listOf(
        DailyForecast("Monday, Oct 23", 22.0, 28.0, "Sunny", "01d"),
        DailyForecast("Tuesday, Oct 24", 20.0, 25.0, "Partly Cloudy", "02d"),
        DailyForecast("Wednesday, Oct 25", 18.0, 23.0, "Showers", "09d")
    )
    WeatherForcastTheme {
        HomeScreenContent(
            uiState = UiState.Success(sampleForecast),
            onCityChanged = {},
            onFetchClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenErrorPreview() {
    WeatherForcastTheme {
        HomeScreenContent(
            uiState = UiState.Error("An error occurred while fetching weather."),
            onCityChanged = {},
            onFetchClick = {}
        )
    }
}
