package com.example.countries.api

class ApiHelper(private val api: CountriesApi) {
    suspend fun getAll() = api.getAllCountries()
}