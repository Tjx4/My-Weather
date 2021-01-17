package com.globalkinetic.myweather.features.previous

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globalkinetic.myweather.database.WeatherDB
import com.globalkinetic.myweather.networking.MyApi
import com.globalkinetic.myweather.repositories.WeatherRepository
import java.lang.IllegalArgumentException

class PreviousWeatherViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PreviousWeatherViewModel::class.java)){
            val weatherRepository = WeatherRepository(MyApi.retrofitHelper, WeatherDB.getInstance(application))
            return PreviousWeatherViewModel(application, weatherRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}