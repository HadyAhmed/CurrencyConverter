package com.hadi.datasource

import com.hadi.model.LatestCurrencies

interface CurrencyConverterDataSource {
    suspend fun fetchCurrencyRates(): LatestCurrencies
}