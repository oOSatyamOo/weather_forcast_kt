package com.github.oOSatyamOo.weatherforcast.di.component

import com.github.oOSatyamOo.weatherforcast.di.network.NetworkModule
import dagger.Component
import jakarta.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

}