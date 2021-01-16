package com.globalkinetic.myweather.extensions

fun nullToDefValue(value: String?, defValue: String = "Unknown"): String {
    return if(value == null || value == "null")  "$defValue" else "$value"
}