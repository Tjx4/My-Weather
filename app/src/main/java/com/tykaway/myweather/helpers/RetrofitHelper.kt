package com.tykaway.myweather.helpers

import com.tykaway.myweather.models.Weather
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitHelper {
    @GET("data/2.5/onecall")
    suspend fun getMyLocationWeather(@Query("appid") appid: String, @Query("lat") lat: Double, @Query("lon") lon: Double): Weather?
}