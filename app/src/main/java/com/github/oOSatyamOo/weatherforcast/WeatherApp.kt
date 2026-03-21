package com.github.oOSatyamOo.weatherforcast

import android.app.Application
import com.github.oOSatyamOo.weatherforcast.di.component.AppComponent
import dagger.hilt.android.HiltAndroidApp
import dagger.Component

@HiltAndroidApp
class WeatherApp : Application()