package com.github.oOSatyamOo.weatherforcast.presentation.ui

import com.github.oOSatyamOo.weatherforcast.data.local.DailyForecast
import com.github.oOSatyamOo.weatherforcast.repo.usecase.GetWeatherForecastUseCase
import com.github.oOSatyamOo.weatherforcast.ui.viewmodel.WeatherViewModel
import com.github.oOSatyamOo.weatherforcast.ui.viewmodel.state.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.test.assertEquals
import kotlin.test.assertIs


@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var useCase: GetWeatherForecastUseCase
    private lateinit var viewModel: WeatherViewModel

    private val fakeForecast = listOf(
        DailyForecast("Mon", 30.0, 20.0, "sunny", "01d")
    )

    @Before
    fun setup() {
        useCase = mockk()
        viewModel = WeatherViewModel(useCase)
    }


    @Test
    fun `fetchWeather show loading when api is called`() = runTest {

        val fakeForecast = listOf(DailyForecast("Mon", 30.0, 20.0, "sunny", "01d"))
        coEvery { useCase(any()) } coAnswers {
            delay(1000) // simulate API delay
            fakeForecast
        }

        viewModel.fetchWeather("London")

        assertIs<UiState.Loading>(viewModel.uiState)
    }

    @Test
    fun `fetchWeather success updates uiState to Success with data`() = runTest {
        coEvery { useCase(any()) } returns fakeForecast

        viewModel.fetchWeather("Delhi")

        advanceUntilIdle()

        val currentState = viewModel.uiState
        assertIs<UiState.Success>(currentState)
        assertEquals(fakeForecast, currentState.forecast)
    }

    @Test
    fun `fetchWeather error updates uiState to Error`() = runTest {
        // Arrange
        val errorMessage = "Network transformation failed"
        coEvery { useCase(any()) } throws Exception(errorMessage)

        // Act
        viewModel.fetchWeather("London")
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState
        assertIs<UiState.Error>(currentState)
        assertEquals(errorMessage, currentState.message)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}