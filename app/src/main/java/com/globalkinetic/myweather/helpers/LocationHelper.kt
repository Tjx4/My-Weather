package com.globalkinetic.myweather.helpers

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*

suspend fun getCurrentLocation(latLong: LatLng, context: Context): String {

    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(latLong.latitude, latLong.longitude, 1)
        val address: Address = addresses[0]
        //var add: String = obj.getAddressLine(0)
        var location = ""

        val area = address.subLocality ?: ""
        if(area.isNullOrEmpty()){
            val city = address.locality ?: ""
            if(city.isNullOrEmpty()){
                val province = address.adminArea ?: ""
                if(province.isNullOrEmpty()){
                    val country = address.countryName ?: ""
                    if(country.isNullOrEmpty()){

                    }
                    else{
                         location = country
                    }
                }
                else{
                     location = province
                }
            }
            else{
                 location = city
            }
        }
        else{
             location = area
        }

        location

    } catch (e: IOException) {
        ""
    }
}