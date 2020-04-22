package com.example.scrap

import com.example.model.Country

class Hasher {

    private var timestamp: Long = 0L
    private lateinit var countries: List<Country>

    fun saveCountries(countries: List<Country>) {
        this.countries = countries
        this.timestamp = System.currentTimeMillis()
    }

    fun getCountries():List<Country>{
        return countries;
    }

    fun isCountriesAvailable() = System.currentTimeMillis() - timestamp < 10000
}