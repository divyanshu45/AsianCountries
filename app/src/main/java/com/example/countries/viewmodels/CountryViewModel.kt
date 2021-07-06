package com.example.countries.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.countries.database.Country
import com.example.countries.repository.CountryRepository
import com.example.countries.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryViewModel(application: Application, private val countryRepository: CountryRepository) : ViewModel() {

    fun getAllCountries() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = countryRepository.getCountries()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun insert(country: Country) = viewModelScope.launch {
        countryRepository.insertCountry(country)
    }

    fun delete(country: Country) = viewModelScope.launch {
        countryRepository.deleteCountry(country)
    }

    fun getAll() = countryRepository.getAll()

}