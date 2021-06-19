package com.globalkinetic.myweather.helpers

import java.text.SimpleDateFormat
import java.util.*

fun getFormattedDate(dt: Long): String{
    val date: Date = Date(dt * 1000)
    val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy h:m:s a")
    return dateFormat.format(date)
}

fun getFormattedTime(dt: Long): String{
    val date: Date = Date(dt * 1000)
    val dateFormat = SimpleDateFormat("h:m:s a")
    return dateFormat.format(date)
}