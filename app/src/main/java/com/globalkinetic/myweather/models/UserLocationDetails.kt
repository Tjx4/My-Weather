package com.globalkinetic.myweather.models

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLocationDetails(
    var name: String?,
    var description: String?,
    var coordinates: LatLng?,
    var dateTime: String?
): Parcelable