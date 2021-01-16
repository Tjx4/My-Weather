package com.globalkinetic.myweather.models

import com.google.gson.annotations.SerializedName

class Weather (
    @SerializedName("timezone") var timezone: String?,
    @SerializedName("current") var current: Current?,
    @SerializedName("hourly") var hourly: List<Current>?,
    var locationName: String?,
)