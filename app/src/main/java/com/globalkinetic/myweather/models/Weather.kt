package com.globalkinetic.myweather.models

import com.google.gson.annotations.SerializedName

data class Weather (
    @SerializedName("timezone") var timezone: String?,
    @SerializedName("current") var current: Current?,
    @SerializedName("hourly") var hourly: List<Current>?,
    @SerializedName("daily") var daily: List<Daily>?,
    var locationName: String?,
)