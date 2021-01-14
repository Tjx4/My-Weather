package com.globalkinetic.myweather.features.weather

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.globalkinetic.myweather.R
import com.globalkinetic.myweather.base.activities.BaseActivity
import com.globalkinetic.myweather.databinding.ActivityWeatherBinding

class WeatherActivity : BaseActivity() {
    private lateinit var binding: ActivityWeatherBinding
    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = WeatherViewModelFactory(application)

        weatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            WeatherViewModel::class.java
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather)
        binding.weatherViewModel = weatherViewModel
        binding.lifecycleOwner = this

        addObservers()
    }

    private fun addObservers() {

    }
}