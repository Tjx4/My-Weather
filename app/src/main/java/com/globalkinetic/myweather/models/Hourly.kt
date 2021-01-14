package com.globalkinetic.myweather.models

import com.google.gson.annotations.SerializedName

class Hourly (
    @SerializedName("main") var main: String?
)