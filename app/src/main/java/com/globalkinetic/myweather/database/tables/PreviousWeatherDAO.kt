package com.globalkinetic.myweather.database.tables

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PreviousWeatherDAO {
    @Insert
    fun insert(superheroesTable: PreviousWeatherTable)

    @Update
    fun update(superheroesTable: PreviousWeatherTable)

    @Delete
    fun delete(superheroesTable: PreviousWeatherTable)

    @Query("SELECT * FROM weather_reports WHERE id = :key")
    fun get(key: Long): PreviousWeatherTable?

    @Query("SELECT * FROM weather_reports ORDER BY id DESC LIMIT 1")
    fun getLastUser(): PreviousWeatherTable?

    @Query("SELECT * FROM weather_reports ORDER BY id DESC")
    fun getAllUsersLiveData(): LiveData<List<PreviousWeatherTable>>

    @Query("SELECT * FROM weather_reports ORDER BY id DESC")
    fun getAllHeroes():List<PreviousWeatherTable>?

    @Query("DELETE  FROM weather_reports")
    fun clear()
}