package com.globalkinetic.myweather.features.weather

import android.app.Application
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.globalkinetic.myweather.base.viewmodels.BaseVieModel
import com.globalkinetic.myweather.constants.API_KEY
import com.globalkinetic.myweather.converter.fahrenheitToCelsius
import com.globalkinetic.myweather.helpers.getAreaName
import com.globalkinetic.myweather.helpers.getFormatedDate
import com.globalkinetic.myweather.models.UserLocation
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.repositories.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel(application: Application, private val weatherRepository: WeatherRepository) : BaseVieModel(
    application
) {

    private var _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _isNoLocation: MutableLiveData<Boolean> = MutableLiveData()
    val isNoLocation: MutableLiveData<Boolean>
        get() = _isNoLocation

    private var _temprature: MutableLiveData<Int> = MutableLiveData()
    val temprature: MutableLiveData<Int>
        get() = _temprature

    private var _weather: MutableLiveData<Weather> = MutableLiveData()
    val weather: MutableLiveData<Weather>
        get() = _weather

    private val _currentLocation: MutableLiveData<UserLocation> = MutableLiveData()
    val currentLocation: MutableLiveData<UserLocation>
        get() = _currentLocation

    private val _description: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String>
        get() = _description

    private var _currentDateTime: MutableLiveData<String> = MutableLiveData()
    val currentDateTime: MutableLiveData<String>
        get() = _currentDateTime

    fun checkAndSetLocation(location: Location?){
        if(location == null){
            _isNoLocation.value = true
            return
        }

        _showLoading.value = true

        val userCoordinates = LatLng(location?.latitude, location?.longitude)
        val locationName = getAreaName(LatLng(location.latitude, location.longitude), app)
        val userLocation = UserLocation(locationName, "", userCoordinates, "")

        _currentLocation.value = userLocation
    }

    fun getAndSetWeather(location: UserLocation){
        _showLoading.value = true
        var currentCoordinates = _currentLocation.value?.coordinates ?: LatLng(0.0, 0.0)

        ioScope.launch {
            val weather = weatherRepository.getWeather(API_KEY, currentCoordinates)

            uiScope.launch {
                _weather.value = weather
                _temprature.value = fahrenheitToCelsius(weather?.current?.temp ?: 0.0)
                _description.value = weather?.current?.weather?.get(0)?.description ?: ""
                _currentDateTime.value =  getFormatedDate(weather?.current?.dt ?: 0)
            }
        }

    }

}