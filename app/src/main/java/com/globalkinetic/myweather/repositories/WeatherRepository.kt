package com.globalkinetic.myweather.repositories

import com.globalkinetic.myweather.database.WeatherDB
import com.globalkinetic.myweather.extensions.toWeather
import com.globalkinetic.myweather.extensions.toWeatherTable
import com.globalkinetic.myweather.networking.RetrofitHelper
import com.globalkinetic.myweather.models.DbOperation
import com.globalkinetic.myweather.models.Weather
import com.google.android.gms.maps.model.LatLng

open class WeatherRepository(var retrofitHelper: RetrofitHelper, var weatherDB: WeatherDB) {

    suspend fun getWeather(apiKey: String, latLng: LatLng) : Weather? {
        return try {
            retrofitHelper.getMyLocationWeather(apiKey, latLng?.latitude, latLng?.longitude, "metric")
        } catch (ex: Exception){
            Weather("", null, null, null, null)
        }
    }

    suspend fun addToPreviousWeatherReports(weather: Weather): DbOperation {
        try {
            weatherDB.previousWeatherDAO.insert(weather.toWeatherTable())
            return DbOperation(true)
        } catch (ex: Exception){
            return DbOperation(false, "$ex")
        }
    }

    suspend fun getPreviousWeatherReports(): List<Weather>? {
        return try {
            val previousReports = ArrayList<Weather>()
            weatherDB.previousWeatherDAO.getAllHeroes()?.let {
                for(previousWeather in  it){
                    previousReports.add(previousWeather.toWeather())
                }
            }

            previousReports
        } catch (ex: Exception){
            null
        }
    }
}