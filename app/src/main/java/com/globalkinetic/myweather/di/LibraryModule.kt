package com.globalkinetic.myweather.di

import com.globalkinetic.myweather.networking.MyApi
import com.globalkinetic.myweather.persistance.room.WeatherDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val networkingModule = module {
    single { MyApi.retrofit }
}

val persistenceModule = module {
    single { WeatherDB.getInstance(androidApplication())}
}