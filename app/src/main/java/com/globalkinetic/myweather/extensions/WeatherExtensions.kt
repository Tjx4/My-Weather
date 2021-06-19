package com.globalkinetic.myweather.extensions

import com.globalkinetic.myweather.converter.temperatureToSingleDecimal
import com.globalkinetic.myweather.persistance.room.tables.PreviousWeatherTable
import com.globalkinetic.myweather.helpers.getFormattedDate
import com.globalkinetic.myweather.models.Current
import com.globalkinetic.myweather.models.Weather
import com.globalkinetic.myweather.models.WeatherInfo

fun Weather.toWeatherTable(): PreviousWeatherTable {
    val previousWeatherTable = PreviousWeatherTable()
    previousWeatherTable.locationName = this.locationName
    previousWeatherTable.dateTime = getFormattedDate(this?.current?.dt ?: 0)
    previousWeatherTable.dt = this?.current?.dt
    previousWeatherTable.temperature = temperatureToSingleDecimal(this?.current?.temp ?: 0.0)
    previousWeatherTable.feelsLike = this.current?.feels_like
    previousWeatherTable.description = "${this.current?.weather?.get(0)?.description}"
    previousWeatherTable.precipitation = this.current?.humidity ?: 0
    previousWeatherTable.uv = this.current?.uvi ?: 0.0
    return previousWeatherTable
}

fun PreviousWeatherTable.toWeather(): Weather {
    val weatherInfo = ArrayList<WeatherInfo>()
    weatherInfo.add(WeatherInfo(this.description, null))
    val current = Current(weatherInfo, this.precipitation, null, this.temperature.toDouble(), this.dt, this.feelsLike, 0.0, this.uv, this.pop)
    return Weather("", current, null, null, locationName)
}