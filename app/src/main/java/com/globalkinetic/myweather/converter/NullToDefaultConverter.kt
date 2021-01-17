package com.globalkinetic.myweather.converter

fun nullToDefValue(value: String?, defValue: String = "Unknown"): String {
    return if(value.isNullOrEmpty() || value == "null")  "$defValue" else "$value"
}
