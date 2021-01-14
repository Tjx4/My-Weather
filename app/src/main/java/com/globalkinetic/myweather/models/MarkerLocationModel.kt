package com.globalkinetic.myweather.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MarkerLocationModel (
    @SerializedName("name")  var name: String? = null,
    @SerializedName("lat")  var lat: Double = 0.0,
    @SerializedName("lng") var lng: Double = 0.0,
) : Parcelable