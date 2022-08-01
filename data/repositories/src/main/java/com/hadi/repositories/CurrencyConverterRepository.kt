package com.hadi.repositories

interface CurrencyConverterRepository {
    suspend fun fetchCurrencyRates(): List<String>
}