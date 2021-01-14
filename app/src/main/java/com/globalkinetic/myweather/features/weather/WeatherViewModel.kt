package com.globalkinetic.myweather.features.weather

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.globalkinetic.myweather.base.viewmodels.BaseVieModel
import com.globalkinetic.myweather.constants.API_KEY
import com.globalkinetic.myweather.models.UserLocation
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.repositories.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application, private val weatherRepository: WeatherRepository) : BaseVieModel(application) {

    private var _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _isNoLocation: MutableLiveData<Boolean> = MutableLiveData()
    val isNoLocation: MutableLiveData<Boolean>
        get() = _isNoLocation

    private var _weather: MutableLiveData<Weather> = MutableLiveData()
    val weather: MutableLiveData<Weather>
        get() = _weather

    private val _currentLocation: MutableLiveData<UserLocation> = MutableLiveData()
    val currentLocation: MutableLiveData<UserLocation>
        get() = _currentLocation

    init {
        val latLng = LatLng(-25.7523497,28.2083034)
        val userLocation = UserLocation("SunnySide", "Decription of this place", latLng, "Wed 11 Jan 13:00 PM")
        checkAndSetLocation(userLocation)
    }

    fun checkAndSetLocation(location: UserLocation?){
        _showLoading.value = true

        if(location == null){
            _isNoLocation.value = true
            return
        }
        _currentLocation.value = location
    }

    fun getAndSetWeather(location: UserLocation){
        _showLoading.value = true
        var currentCoordinates = _currentLocation.value?.coordinates ?: LatLng(0.0, 0.0)

        ioScope.launch {
            val weather = weatherRepository.getWeather(API_KEY, currentCoordinates)

            uiScope.launch {
                _weather.value = weather
            }
        }

    }

}