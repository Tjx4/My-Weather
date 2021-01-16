package com.globalkinetic.myweather.features.previous

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.globalkinetic.myweather.base.viewmodels.BaseVieModel
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.repositories.WeatherRepository
import kotlinx.coroutines.launch

class PreviousWeatherViewModel(application: Application, private val weatherRepository: WeatherRepository) : BaseVieModel(application) {

    private var _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private val _previousWeatherReports: MutableLiveData<List<Weather>> = MutableLiveData()
    val previousWeatherReports: MutableLiveData<List<Weather>>
        get() = _previousWeatherReports

    private val _isNoPrevious: MutableLiveData<Boolean> = MutableLiveData()
    val isNoPrevious: MutableLiveData<Boolean>
        get() = _isNoPrevious

    init {
        getPreviousWeatherReports()
    }

    fun getPreviousWeatherReports(){
        _showLoading.value = true

        ioScope.launch {
            var previousWeatherReports = weatherRepository.getPreviousWeatherReports()

            uiScope.launch {
                if(!previousWeatherReports.isNullOrEmpty()){
                    _previousWeatherReports.value = previousWeatherReports
                }
                else{
                    _isNoPrevious.value = true
                }
            }
        }
    }
}