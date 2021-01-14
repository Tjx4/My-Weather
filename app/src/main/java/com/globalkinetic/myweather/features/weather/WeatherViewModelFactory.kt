package com.globalkinetic.myweather.features.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globalkinetic.myweather.helpers.MyApi
import com.globalkinetic.myweather.repositories.WeatherRepository
import java.lang.IllegalArgumentException

class WeatherViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            val weatherRepository = WeatherRepository(MyApi.retrofitHelper)
            return WeatherViewModel(application, weatherRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}