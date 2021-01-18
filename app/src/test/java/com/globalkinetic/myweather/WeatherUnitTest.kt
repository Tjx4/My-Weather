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

    @Test
    fun `test get location`(){
        val location = Location("user location")
        location.latitude = -25.7402069
        location.longitude = 28.2098021
        val testUserLocationDetails = UserLocationDetails(
            "Some town", "Some description", LatLng(
                location.latitude,
                location.longitude
            ), ""
        )

        whenever(weatherViewModel.getUserLocationDetails(location)).thenReturn(testUserLocationDetails)
        weatherViewModel.checkAndSetLocation(location)

        assertEquals(testUserLocationDetails, weatherViewModel.currentLocationDetails.value)
    }

    @Test
    fun `test null location does not set userlocationDetails`() {
        val location = null

        weatherViewModel.getUserLocationDetails(location)

        assertEquals(null, weatherViewModel.currentLocationDetails.value)
    }


    @Test
    fun `test addWeather To PreviousList`() = runBlocking  {
        weatherViewModel.weather.value = Weather(null, null, null, null, null)

        weatherViewModel.addWeatherToPreviousList()

        assert(weatherViewModel.isWeatherAdded.value!!)
    }

    @Test
    fun `test error message set on fail to add to previousList`() = runBlocking  {
        val errorMessage = "error"
        val weather = Weather(null, null, null, null, null)

        whenever(weatherViewModel.weatherRepository.addToPreviousWeatherReports(weather)).thenReturn(
            DbOperation(false, errorMessage)
        )
        weatherViewModel.addWeatherToPreviousList()

        assertEquals(weatherViewModel.dbError.value, errorMessage)
    }
}