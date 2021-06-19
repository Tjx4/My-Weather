package com.globalkinetic.myweather.persistance.room.tables

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PreviousWeatherDAO {
    @Insert
    fun insert(previousWeatherTable: PreviousWeatherTable)

    @Update
    fun update(previousWeatherTable: PreviousWeatherTable)

    @Delete
    fun delete(previousWeatherTable: PreviousWeatherTable)

    @Query("SELECT * FROM weather_reports WHERE id = :key")
    fun get(key: Long): PreviousWeatherTable?

    @Query("SELECT * FROM weather_reports ORDER BY id DESC LIMIT 1")
    fun getLastUser(): PreviousWeatherTable?

    @Query("SELECT * FROM weather_reports ORDER BY id DESC")
    fun getAllUsersLiveData(): LiveData<List<PreviousWeatherTable>>

    @Query("SELECT * FROM weather_reports ORDER BY id DESC")
    fun getAll():List<PreviousWeatherTable>?

    @Query("DELETE  FROM weather_reports")
    fun clear()
}