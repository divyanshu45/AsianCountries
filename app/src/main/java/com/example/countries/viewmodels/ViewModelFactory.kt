package com.example.countries.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countries.api.ApiHelper
import com.example.countries.api.CountriesApi
import com.example.countries.repository.CountryRepository

class ViewModelFactory(val app: Application, val countryRepository: CountryRepository)  : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CountryViewModel(app, countryRepository) as T
    }
}