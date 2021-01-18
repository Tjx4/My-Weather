package com.globalkinetic.myweather.helpers

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

fun checkLocationPermissionAndContinue(context: Activity, onSuccessCallback: () -> Unit = {}) {
     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            onSuccessCallback.invoke()
        } else {
            ActivityCompat.requestPermissions(
                    context,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
            )
        }
    } else {
         onSuccessCallback.invoke()
    }
}

