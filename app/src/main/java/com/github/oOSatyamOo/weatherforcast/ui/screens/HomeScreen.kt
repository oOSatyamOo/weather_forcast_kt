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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.github.oOSatyamOo.weatherforcast.data.local.DailyForecast
import com.github.oOSatyamOo.weatherforcast.ui.viewmodel.state.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    var city by remember { mutableStateOf("Bengaluru") }   // Default city for you
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather Forecast") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
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
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Enter City Name") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { viewModel.fetchWeather(city) },
                    enabled = city.isNotBlank()
                ) {
                    Text("Fetch")
                }
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
                    Button(onClick = { viewModel.fetchWeather(city) }) {
                        Text("Retry")
                    }
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
                model = "https://openweathermap.org/img/wn/${day.icon}@2x.png",
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
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = day.condition.replaceFirstChar { if (it.isLowerCase()) it.titlecase(java.util.Locale.ROOT) else it.toString() },   // ← Simple & clean way
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}