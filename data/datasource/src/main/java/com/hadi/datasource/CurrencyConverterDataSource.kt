package com.hadi.datasource

interface CurrencyConverterDataSource {
    suspend fun fetchCurrencyRates(): List<String>
}