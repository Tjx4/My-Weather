package com.globalkinetic.myweather.persistance.room.tables

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "weather_reports")
data class PreviousWeatherTable (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long = 0L,
    @ColumnInfo(name = "locationName")
    var locationName:String? = null,
    @ColumnInfo(name = "dateTime")
    var dateTime:String? = null,
    @ColumnInfo(name = "dt")
    var dt: Long? = 0,
    @ColumnInfo(name = "temperature")
    var temperature:Int = 0,
    @ColumnInfo(name = "feelsLike")
    var feelsLike:Double? = 0.0,
    @ColumnInfo(name = "description")
    var description:String? = null,
    @ColumnInfo(name = "precipitation")
    var precipitation:Int = 0,
    @ColumnInfo(name = "pop")
    var pop:Double = 0.0,
    @ColumnInfo(name = "uv")
    var uv:Double = 0.0,
): Parcelable