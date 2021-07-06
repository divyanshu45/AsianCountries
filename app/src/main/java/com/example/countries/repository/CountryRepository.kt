package com.example.countries.repository

import com.example.countries.api.ApiHelper
import com.example.countries.api.CountriesApi
import com.example.countries.database.Country
import com.example.countries.database.CountryDatabase

class CountryRepository(private val apiHelper: ApiHelper, val db: CountryDatabase) {

    suspend fun getCountries() = apiHelper.getAll()

    suspend fun insertCountry(country: Country) = db.getExpenseDao().insertCountry(country)

    suspend fun deleteCountry(country: Country) = db.getExpenseDao().deleteCountry(country)

    fun getAll() = db.getExpenseDao().getAllCountries()
}