package com.globalkinetic.myweather.models

import com.google.gson.annotations.SerializedName

class WeatherInfo (
    @SerializedName("description") var description: String?,
    @SerializedName("main") var main: String?
)