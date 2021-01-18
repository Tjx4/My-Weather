package com.globalkinetic.myweather

import android.app.Application
import android.location.Location
import com.globalkinetic.myweather.features.weather.WeatherViewModel
import com.globalkinetic.myweather.models.UserLocationDetails
import com.globalkinetic.myweather.repositories.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
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
    fun `test get location`() = runBlocking  {

    }

    @Test
    fun `test null location does not set userlocation`() = runBlocking {
        val location = null

        weatherViewModel.getUserLocationDetails(location)

        assertEquals(null, weatherViewModel.currentLocationDetails.value)
    }

    @Test
    fun `test`() {
        assertEquals(4, 2 + 2)
    }
}