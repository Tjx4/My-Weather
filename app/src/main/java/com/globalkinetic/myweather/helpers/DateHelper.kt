package com.globalkinetic.myweather.helpers

import java.text.SimpleDateFormat
import java.util.*

fun getFormatedDate(dt: Long): String{
    val date: Date = Date(dt * 1000)
    val date_format = SimpleDateFormat("EEEE, d MMMM yyyy h:m:s a")
    val dateText: String = date_format.format(date)
    return dateText
}

fun getFormatedTime(dt: Long): String{
    val date: Date = Date(dt * 1000)
    val date_format = SimpleDateFormat("h:m:s a")
    val dateText: String = date_format.format(date)
    return dateText
}