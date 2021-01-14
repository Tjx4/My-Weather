package com.globalkinetic.myweather.features.weather

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.globalkinetic.myweather.base.viewmodels.BaseVieModel
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.repositories.WeatherRepository

class WeatherViewModel(application: Application, private val weatherRepository: WeatherRepository) : BaseVieModel(application) {

    private var _showLoading: MutableLiveData<Boolean> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean>
        get() = _showLoading

    private var _weather: MutableLiveData<Weather> = MutableLiveData()
    val weather: MutableLiveData<Weather>
        get() = _weather

    init {

    }

}