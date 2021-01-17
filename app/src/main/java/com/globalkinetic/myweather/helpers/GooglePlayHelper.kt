package com.globalkinetic.myweather.helpers

import android.app.Activity
import com.globalkinetic.myweather.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability


fun checkGoogleApi(activity: Activity, onSuccessCallback: () -> Unit = {}) {
    val api = GoogleApiAvailability.getInstance()
    val isAv = api.isGooglePlayServicesAvailable(activity)

    if (isAv == ConnectionResult.SUCCESS) {
        onSuccessCallback.invoke()

    } else if (api.isUserResolvableError(isAv)) {
        showErrorDialog(
            activity,
            activity.getString(R.string.google_play_error),
            activity.getString(R.string.google_play_error_message),
            activity.getString(R.string.close)
        ) {
            activity.finish()
        }

    } else {
        showErrorDialog(
            activity,
            activity.getString(R.string.google_play_error),
            activity.getString(R.string.google_play_error_message),
            activity.getString(R.string.close)
        ) {
            activity.finish()
        }

    }
}

