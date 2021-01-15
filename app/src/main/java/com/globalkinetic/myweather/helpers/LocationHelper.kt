package com.globalkinetic.myweather.helpers

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*

fun getAreaName(latLong: LatLng, context: Context): String {
    var area = ""
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses: List<Address> = geocoder.getFromLocation(latLong.latitude, latLong.longitude, 1)
        val address: Address = addresses[0]
        //var add: String = obj.getAddressLine(0)
        area = address.subLocality
        //obj.getAdminArea()
    } catch (e: IOException) {
    }

    return area
}