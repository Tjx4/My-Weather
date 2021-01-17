package com.globalkinetic.myweather.models

import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("pop") var pop: Double?
)