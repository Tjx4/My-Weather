package com.tykaway.myweather.models

import com.google.gson.annotations.SerializedName

class WeatherInfo (
    @SerializedName("description") var description: String?
)