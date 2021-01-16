package com.globalkinetic.myweather.repositories

import com.globalkinetic.myweather.database.WeatherDB
import com.globalkinetic.myweather.extensions.toWeather
import com.globalkinetic.myweather.extensions.toWeatherTable
import com.globalkinetic.myweather.helpers.RetrofitHelper
import com.globalkinetic.myweather.models.DbOperation
import com.globalkinetic.myweather.models.Weather
import com.google.android.gms.maps.model.LatLng

class WeatherRepository(private val retrofitHelper: RetrofitHelper, private val weatherDB: WeatherDB) {

    suspend fun getWeather(apiKey: String, latLng: LatLng) : Weather? {
        return try {
            retrofitHelper.getMyLocationWeather(apiKey, latLng?.latitude, latLng?.longitude)
        } catch (ex: Exception){
            Weather("", null, null, null)
        }
    }

    suspend fun addToPreviousReports(weather: Weather): DbOperation {
        return try {
            weatherDB.previousWeatherDAO.insert(weather.toWeatherTable())
            DbOperation(true)
        } catch (ex: Exception){
            DbOperation(false)
        }
    }

    suspend fun getPreviousReports(): List<Weather>? {
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