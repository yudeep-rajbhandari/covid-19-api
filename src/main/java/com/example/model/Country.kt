package com.example.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Country(
        @JsonProperty("Country,_Other")
        val countryName: String,

        @JsonProperty("New_Cases")
        val newCases: String,
        @JsonProperty("New_Deaths")
        val newDeaths: String,
        @JsonProperty("Total_Cases")
        val totalCases: String,
        @JsonProperty("Total_Deaths")
        val totalDeaths: String,
        @JsonProperty("Total_Recovered")
        val totalRecovered: String,
        @JsonProperty("Active_Cases")
        val activeCases: String,
        @JsonProperty("Serious,_Critical")
        val criticalCases: String,

        @JsonProperty("Tot_Cases/_1M_pop")
        val totalCasesPerMillion: String,
        @JsonProperty("Deaths/_1M_pop")
        val totalDeathsPerMillion: String,
        @JsonProperty("Reported_1st_case")
        val firstCase: String
) {
    fun copy() = Country(countryName, newCases, newDeaths, totalCases, totalDeaths, totalRecovered, activeCases, criticalCases, totalCasesPerMillion, totalDeathsPerMillion, firstCase)
}