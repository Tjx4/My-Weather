package com.globalkinetic.myweather

import android.app.Application
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.globalkinetic.myweather.constants.API_KEY
import com.globalkinetic.myweather.persistance.room.WeatherDB
import com.globalkinetic.myweather.features.weather.WeatherViewModel
import com.globalkinetic.myweather.models.Current
import com.globalkinetic.myweather.models.DbOperation
import com.globalkinetic.myweather.models.UserLocationDetails
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.networking.RetrofitHelper
import com.globalkinetic.myweather.repositories.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class WeatherUnitTest  {
    lateinit var weatherViewModel: WeatherViewModel

    @Mock
    lateinit var retrofitHelper: RetrofitHelper
    @Mock
    lateinit var weatherDB: WeatherDB
    @Mock
    lateinit var weatherRepository: WeatherRepository

    @Mock
    lateinit var application: Application

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        weatherRepository.retrofitHelper = retrofitHelper
        weatherRepository.weatherDB = weatherDB
        weatherViewModel = WeatherViewModel(application, weatherRepository)
    }

    @Test
    fun `test set user location details`(){
        val location = Location("")
        location.latitude = -25.7611586
        location.longitude = 28.2088192
        val userLocationDetails = UserLocationDetails(
            "Some location", "", LatLng(
                location.latitude,
                location.longitude
            ), ""
        )

       weatherViewModel.checkAndSetLocation(userLocationDetails)

        assertEquals(weatherViewModel.userLocationDetails.value, userLocationDetails)
    }

    @Test
    fun `test user location details null`(){
        val userLocationDetails: UserLocationDetails? = null

        weatherViewModel.checkAndSetLocation(userLocationDetails)

        assert(weatherViewModel.isLocationError.value!!)
    }

    @Test
    fun `test set weather`() = runBlocking {

            val weather: Weather? = Weather(
                    "",
                    Current(null, 0, 0, 0.0, 0, 0.0, 0.0, 0.0, 0.0),
                    ArrayList(),
                    ArrayList(),
                    "Some location"
            )

            weatherViewModel.setWeatherReport(weather)


            assertEquals(weather, weatherViewModel.weather.value)
    }

    @Test
    fun `test get weather fail`() = runBlocking {
        val weather: Weather? = null
        var currentCoordinates = LatLng(0.0, 0.0)

        whenever(weatherRepository.getWeather(API_KEY, currentCoordinates)).thenReturn(weather)
        weatherViewModel.setWeatherReport(weather)

        assertEquals(weatherViewModel.weather.value, null)
    }

    @Test
    fun `test add weather to previous list`() = runBlocking {

        val dbOperation = DbOperation(true, "")
        /*
             val weather = Weather(
                 "",
                 Current(null, 0, 0, 0.0, 0, 0.0, 0.0, 0.0, 0.0),
                 ArrayList(),
                 ArrayList(),
                 "Some location"
             )


             whenever(weatherRepository.addToPreviousWeatherReports(weather)).then{
                 weatherViewModel.isWeatherAdded.value = true
                 DbOperation(true, "")
             }
         */
        weatherViewModel.checkIsWeatherAddSuccessful(dbOperation)

        assertEquals(true, weatherViewModel.isWeatherAdded.value)
    }

}