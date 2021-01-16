package com.globalkinetic.myweather.converter

fun fahrenheitToCelsius(fahrenheit: Double): Int{
    return  try {
        var celsius = (fahrenheit - 32.0) * 5.0 / 9.0
        celsius /= 5.2
        celsius.toInt()
    }
    catch (ex: Exception){
        fahrenheit.toInt()
    }
}