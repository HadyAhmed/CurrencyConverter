package com.hadi.repositories

import com.hadi.model.LatestCurrencies

interface CurrencyConverterRepository {
    suspend fun fetchCurrencyRates(base: String? = null): LatestCurrencies
}