package com.globalkinetic.myweather.features.weather

import android.app.Application
import com.globalkinetic.myweather.base.viewmodels.BaseVieModel
import com.globalkinetic.myweather.repositories.WeatherRepository

class WeatherViewModel(application: Application, private val weatherRepository: WeatherRepository) : BaseVieModel(application) {



}