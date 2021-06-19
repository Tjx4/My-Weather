package com.globalkinetic.myweather.di

import com.globalkinetic.myweather.repositories.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { WeatherRepository(get(), get()) }
}