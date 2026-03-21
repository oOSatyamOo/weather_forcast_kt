package com.github.oOSatyamOo.weatherforcast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.oOSatyamOo.weatherforcast.ui.screens.HomeScreen
import com.github.oOSatyamOo.weatherforcast.ui.theme.WeatherForcastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint          // ← This is REQUIRED
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherForcastTheme {  // your theme
                HomeScreen()
            }
        }
    }
}