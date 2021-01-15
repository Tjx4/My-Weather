package com.globalkinetic.myweather.features.previous

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class PreviousWeatherViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PreviousWeatherViewModel::class.java)){

            return PreviousWeatherViewModel(application) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}