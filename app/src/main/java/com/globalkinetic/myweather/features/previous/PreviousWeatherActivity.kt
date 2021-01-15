package com.globalkinetic.myweather.features.previous

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.globalkinetic.myweather.R
import com.globalkinetic.myweather.base.activities.BaseChildActivity
import com.globalkinetic.myweather.databinding.ActivityPreviousWeatherBinding

class PreviousPreviousWeatherActivity : BaseChildActivity() {
    private lateinit var binding: ActivityPreviousWeatherBinding
    private lateinit var previousWeatherViewModel: PreviousWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var application = requireNotNull(this).application
        var viewModelFactory = PreviousWeatherViewModelFactory(application)

        previousWeatherViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            PreviousWeatherViewModel::class.java
        )
        binding = DataBindingUtil.setContentView(this, R.layout.activity_previous_weather)
        binding.previousWeatherViewModel = previousWeatherViewModel
        binding.lifecycleOwner = this

        //addObservers()
    }
}