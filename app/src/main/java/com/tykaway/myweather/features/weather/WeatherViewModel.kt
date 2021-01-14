package com.tykaway.myweather.features.weather

import android.app.Application
import com.tykaway.myweather.base.viewmodels.BaseVieModel
import com.tykaway.myweather.repositories.WeatherRepository

class WeatherViewModel(application: Application, private val weatherRepository: WeatherRepository) : BaseVieModel(application) {
}