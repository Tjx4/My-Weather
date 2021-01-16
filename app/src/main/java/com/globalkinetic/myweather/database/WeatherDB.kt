package com.globalkinetic.myweather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.globalkinetic.myweather.database.tables.PreviousWeatherDAO
import com.globalkinetic.myweather.database.tables.PreviousWeatherTable

@Database(entities = [PreviousWeatherTable::class], version = 1, exportSchema = false)
abstract class WeatherDB : RoomDatabase() {
    abstract val previousWeatherDAO: PreviousWeatherDAO

    companion object{
        @Volatile
        private var INSTANCE: WeatherDB? = null

        fun getInstance(context: Context): WeatherDB {
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext, WeatherDB::class.java, "weather_db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return  instance
            }
        }
    }

}