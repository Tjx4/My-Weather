package com.globalkinetic.myweather.converter

fun fahrenheitToCelsius(temp: Double): Int{
    return  try {
        val fahrenheit = temp / 3
        val celsius = (fahrenheit / 2) * 5.0 / 9.0
        celsius.toInt()
    }
    catch (ex: Exception){
        temp.toInt()
    }
}