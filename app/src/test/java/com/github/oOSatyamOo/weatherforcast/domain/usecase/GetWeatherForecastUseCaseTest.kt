package com.github.oOSatyamOo.weatherforcast.domain.usecase

import com.github.oOSatyamOo.weatherforcast.data.local.DailyForecast
import com.github.oOSatyamOo.weatherforcast.repo.WeatherRepo
import com.github.oOSatyamOo.weatherforcast.repo.usecase.GetWeatherForecastUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class GetWeatherForecastUseCaseTest {

    private lateinit var repository: WeatherRepo
    private lateinit var useCase: GetWeatherForecastUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetWeatherForecastUseCase(repository)
    }

    @Test
    fun `useCase should call repository with correct city`() = runTest {
        coEvery { repository.getForecast("Bengaluru") } returns emptyList()

        useCase("Bengaluru")

        coVerify(exactly = 1) { repository.getForecast("Bengaluru") }
    }


    @Test
    fun `when repository returns data, useCase should return same data`(): Unit = runTest {
        val fakeData = listOf(
            DailyForecast("Monday", 25.0, 18.0, "clear sky", "01d")
        )
        coEvery { repository.getForecast("Bengaluru") } returns fakeData

        val result = useCase("Bengaluru")

        assertEquals(fakeData, result)
    }

    @Test
    fun `when repository returns empty list, useCase should return empty list`() = runTest {
        coEvery { repository.getForecast("Bengaluru") } returns emptyList()

        val result = useCase("Bengaluru")

        assertTrue(result.isEmpty())
    }

    @Test
    fun `when repository throws exception, useCase should throw same exception`() = runTest {
        coEvery { repository.getForecast("") } throws IllegalArgumentException("API Error")

        assertFailsWith<RuntimeException> {
            useCase("")
        }
    }

    @Test
    fun `useCase should return consistent data for same input`() = runTest {
        val fakeData = listOf(
            DailyForecast("Monday", 25.0, 18.0, "clear sky", "01d")
        )

        coEvery { repository.getForecast("Bengaluru") } returns fakeData

        val result1 = useCase("Bengaluru")
        val result2 = useCase("Bengaluru")

        assertEquals(result1, result2)
    }
}