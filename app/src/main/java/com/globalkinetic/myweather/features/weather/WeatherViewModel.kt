package com.globalkinetic.myweather.features.weather

import android.app.Application
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.globalkinetic.myweather.R
import com.globalkinetic.myweather.base.viewmodels.BaseVieModel
import com.globalkinetic.myweather.constants.API_KEY
import com.globalkinetic.myweather.converter.fahrenheitToCelsius
import com.globalkinetic.myweather.helpers.getAreaName
import com.globalkinetic.myweather.helpers.getFormatedDate
import com.globalkinetic.myweather.models.Current
import com.globalkinetic.myweather.models.UserLocation
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.repositories.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_weather.view.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel(application: Application, private val weatherRepository: WeatherRepository) : BaseVieModel(
    application
) {

    private var _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _isLocationError: MutableLiveData<Boolean> = MutableLiveData()
    val isLocationError: MutableLiveData<Boolean>
        get() = _isLocationError

    private val _isWeatherError: MutableLiveData<Boolean> = MutableLiveData()
    val isWeatherError: MutableLiveData<Boolean>
        get() = _isWeatherError

    private val _isWeatherAdded: MutableLiveData<Boolean> = MutableLiveData()
    val isWeatherAdded: MutableLiveData<Boolean>
        get() = _isWeatherAdded

    private var _temprature: MutableLiveData<Int> = MutableLiveData()
    val temprature: MutableLiveData<Int>
        get() = _temprature

    private var _weather: MutableLiveData<Weather?> = MutableLiveData()
    val weather: MutableLiveData<Weather?>
        get() = _weather

    private var _hourly: MutableLiveData<List<Current>?> = MutableLiveData()
    val hourly: MutableLiveData<List<Current>?>
        get() = _hourly

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
            _isLocationError.value = true
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
            var weather: Weather? = weatherRepository.getWeather(API_KEY, currentCoordinates)

            uiScope.launch {
                if(weather != null){
                    _weather.value = weather
                    _weather.value?.locationName = _currentLocation.value?.name
                    _temprature.value = fahrenheitToCelsius(weather?.current?.temp ?: 0.0)
                    _currentDateTime.value = getFormatedDate(weather?.current?.dt ?: 0)
                    _description.value = weather?.current?.weather?.get(0)?.description ?: ""

                    weather?.hourly?.let {
                        if(it.isNotEmpty()){
                            _hourly.value = it
                        }
                    }

                }
                else{
                    _isWeatherError.value = true
                }
            }
        }

    }

    fun addWeatherToPreviousList() {
        weather?.let {
            ioScope.launch {
                _weather.value?.let {
                    var addWeather = weatherRepository.addToPreviousWeatherReports(it)

                    uiScope.launch {
                        if (addWeather.isSuccessful){
                            _isWeatherAdded.value = true
                        }
                        else{
                            //Weather add error
                        }
                    }
                }
            }
        }
    }

}