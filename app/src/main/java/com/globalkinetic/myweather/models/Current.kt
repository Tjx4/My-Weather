package com.globalkinetic.myweather.models

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("weather") var weather: List<WeatherInfo?>,
    @SerializedName("humidity") var humidity: Int?,
    @SerializedName("clouds") var clouds: Int?,
    @SerializedName("temp") var temp: Double?,
    @SerializedName("feels_like") var feels_like: Double?,
    @SerializedName("wind_speed") var wind_speed: Double?,
    @SerializedName("uvi") var uvi: Double?,
    @SerializedName("pop") var pop: Double?
)