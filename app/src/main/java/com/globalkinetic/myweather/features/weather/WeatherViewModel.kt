package com.globalkinetic.myweather.features.weather

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.globalkinetic.myweather.base.viewmodels.BaseVieModel
import com.globalkinetic.myweather.constants.API_KEY
import com.globalkinetic.myweather.converter.temperatureToSingleDecimal
import com.globalkinetic.myweather.helpers.getFormatedDate
import com.globalkinetic.myweather.models.Current
import com.globalkinetic.myweather.models.DbOperation
import com.globalkinetic.myweather.models.UserLocationDetails
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.repositories.WeatherRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application, val weatherRepository: WeatherRepository) : BaseVieModel(
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

    private val _dbError: MutableLiveData<String> = MutableLiveData()
    val dbError: MutableLiveData<String>
        get() = _dbError

    private var _temprature: MutableLiveData<Int> = MutableLiveData()
    val temprature: MutableLiveData<Int>
        get() = _temprature

    private var _weather: MutableLiveData<Weather?> = MutableLiveData()
    val weather: MutableLiveData<Weather?>
        get() = _weather

    private var _hourly: MutableLiveData<List<Current>?> = MutableLiveData()
    val hourly: MutableLiveData<List<Current>?>
        get() = _hourly

    private val _userLocationDetails: MutableLiveData<UserLocationDetails> = MutableLiveData()
    val userLocationDetails: MutableLiveData<UserLocationDetails>
        get() = _userLocationDetails

    private val _description: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String>
        get() = _description

    private val _precipitation: MutableLiveData<String> = MutableLiveData()
    val precipitation: MutableLiveData<String>
        get() = _precipitation

    private var _currentDateTime: MutableLiveData<String> = MutableLiveData()
    val currentDateTime: MutableLiveData<String>
        get() = _currentDateTime

    fun checkAndSetLocation(userLocationDetails: UserLocationDetails?) {
        if (userLocationDetails == null) {
            _isLocationError.value = true
            return
        }

        _userLocationDetails.value = userLocationDetails
    }

    fun showLoaderAndGetWeather(locationDetails: UserLocationDetails){
        _showLoading.value = true

        var currentCoordinates = locationDetails.coordinates ?: LatLng(0.0, 0.0)

        ioScope.launch {

            var weather = weatherRepository.getWeather(API_KEY, currentCoordinates)

            uiScope.launch {
                setWeatherReport(weather)
            }
        }
    }

    fun setWeatherReport(weather: Weather?) {
            if (weather != null) {
                _weather.value = weather
                _weather.value?.locationName = _userLocationDetails.value?.name
                _temprature.value = temperatureToSingleDecimal(weather?.current?.temp ?: 0.0)
                _currentDateTime.value = getFormatedDate(weather?.current?.dt ?: 0)
                _description.value = weather?.current?.weather?.get(0)?.description ?: ""

                weather?.daily?.let {
                    if (it.isNotEmpty()) {
                        _precipitation.value = "${it[0]?.pop ?: 0}"
                    }
                }

                weather?.hourly?.let {
                    if (it.isNotEmpty()) {
                        _hourly.value = it
                    }
                }

            } else {
                _isWeatherError.value = true
            }
    }

    fun checkAndAddWeatherToPreviousList() {
        _weather?.value?.let {
            ioScope.launch {
                var addWeather = weatherRepository.addToPreviousWeatherReports(it)

                uiScope.launch {
                    checkIsWeatherAddSuccessful(addWeather)
                }
            }
        }
    }

     fun checkIsWeatherAddSuccessful(dbOperation: DbOperation) {
        if (dbOperation.isSuccessful) {
            _isWeatherAdded.value = true
        } else {
            _dbError.value = dbOperation.errorMessage
        }
    }

}