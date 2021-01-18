package com.globalkinetic.myweather

import android.app.Application
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globalkinetic.myweather.features.weather.WeatherViewModel
import com.globalkinetic.myweather.models.DbOperation
import com.globalkinetic.myweather.models.UserLocationDetails
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.repositories.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
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

}