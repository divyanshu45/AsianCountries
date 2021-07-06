package com.example.countries.database

import androidx.room.*

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: Country)

    @Delete
    suspend fun deleteCountry(country: Country)

    @Query("SELECT * FROM all_countries")
    fun getAllCountries(): List<Country>
}