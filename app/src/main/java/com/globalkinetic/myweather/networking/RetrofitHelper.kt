package com.globalkinetic.myweather.networking

import com.globalkinetic.myweather.models.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitHelper {
    @GET("data/2.5/onecall")
    suspend fun getMyLocationWeather(@Query("appid") appid: String, @Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String): Weather?
}