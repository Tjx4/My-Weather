package com.globalkinetic.myweather

import android.app.Application
import com.globalkinetic.myweather.features.weather.WeatherViewModel
import com.globalkinetic.myweather.repositories.WeatherRepository
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherUnitTest {

    lateinit var weatherViewModel: WeatherViewModel

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        weatherViewModel = WeatherViewModel(application, weatherRepository)
    }

    @Test
    fun `test`() {
        assertEquals(4, 2 + 2)
    }
}