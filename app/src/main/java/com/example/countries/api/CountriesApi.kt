package com.example.countries.api

import com.example.countries.models.CountryItem
import retrofit2.http.GET

interface CountriesApi {

    @GET("rest/v2/region/asia")
    suspend fun getAllCountries() : ArrayList<CountryItem>
}