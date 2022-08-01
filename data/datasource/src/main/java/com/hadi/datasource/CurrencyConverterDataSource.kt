package com.hadi.datasource

import com.hadi.model.DataResult
import com.hadi.model.LatestCurrencies

interface CurrencyConverterDataSource {
    suspend fun fetchCurrencyRates(base: String? = null): DataResult<LatestCurrencies>
}