package com.globalkinetic.myweather.helpers

import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.globalkinetic.myweather.R

fun isGPSOn(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val lm = context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        lm.isLocationEnabled
    } else {
        val mode = Settings.Secure.getInt(
            context.contentResolver, Settings.Secure.LOCATION_MODE,
            Settings.Secure.LOCATION_MODE_OFF
        )
        mode != Settings.Secure.LOCATION_MODE_OFF
    }
}

fun checkGPSAndProceed(context: Context, onSuccessCallback: () -> Unit = {}) {
    if (isGPSOn(context)) {
        onSuccessCallback.invoke()
    } else {
        showErrorAlert(
            context,
            context.getString(R.string.gps_error_title),
            context.getString(R.string.gps_error_message),
            context.getString(R.string.try_again)
        ) {
            checkGPSAndProceed(context, onSuccessCallback)
        }
    }
}
