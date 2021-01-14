package com.globalkinetic.myweather.converter

fun fahrenheitToCelsius(fahrenheit: Double): Int{
    val celsius = (fahrenheit - 32.0) * 5.0 / 9.0
    return celsius.toInt()
}