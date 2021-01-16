package com.globalkinetic.myweather.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DbOperation(
        var isSuccessful: Boolean = false
): Parcelable