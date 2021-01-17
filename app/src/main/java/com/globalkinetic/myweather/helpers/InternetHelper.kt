package com.globalkinetic.myweather.helpers

import android.content.Context
import android.net.ConnectivityManager
import com.globalkinetic.myweather.R

fun isNetworkAvailable(context: Context, onSuccessCallback: () -> Unit = {}) {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val isConnected = connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
        .isConnected

    if(isConnected){
        onSuccessCallback.invoke()
    }
    else{
        showErrorAlert(
            context,
            context.getString(R.string.internet_error),
            context.getString(R.string.internet_error_message),
            context.getString(R.string.try_again)
        ) {
            isNetworkAvailable(context, onSuccessCallback)
        }
    }
}

