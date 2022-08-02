package com.hadi.repositories

import com.hadi.model.DataResult
import com.hadi.model.HistoricalData
import com.hadi.model.HistoricalDataRequest
import com.hadi.model.LatestCurrencies

interface CurrencyConverterRepository {
    suspend fun fetchCurrencyRates(base: String? = null): DataResult<LatestCurrencies>
    suspend fun fetchHistoricalData(request: HistoricalDataRequest): DataResult<HistoricalData>
}