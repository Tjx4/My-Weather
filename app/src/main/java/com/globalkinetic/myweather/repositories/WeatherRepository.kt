package com.globalkinetic.myweather.repositories

import com.globalkinetic.myweather.helpers.RetrofitHelper
import com.globalkinetic.myweather.models.Weather
import com.google.android.gms.maps.model.LatLng

class WeatherRepository(var retrofitHelper: RetrofitHelper) {

    suspend fun getWeather(apiKey: String, latLng: LatLng) : Weather? {
        return try {
            retrofitHelper.getMyLocationWeather(apiKey, latLng?.latitude, latLng?.longitude)
        } catch (ex: Exception){
            Weather("", null)
        }
    }

}