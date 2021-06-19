package com.globalkinetic.myweather.di

import com.globalkinetic.myweather.features.weather.WeatherViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel(androidApplication(), get()) }
}
