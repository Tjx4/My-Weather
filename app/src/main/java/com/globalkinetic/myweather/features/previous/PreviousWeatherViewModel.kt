package com.globalkinetic.myweather.features.previous

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.globalkinetic.myweather.base.viewmodels.BaseVieModel
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun getPreviousWeatherReports(){
        _showLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            var previousWeatherReports = weatherRepository.getPreviousWeatherReports()

            withContext(Dispatchers.Main) {
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