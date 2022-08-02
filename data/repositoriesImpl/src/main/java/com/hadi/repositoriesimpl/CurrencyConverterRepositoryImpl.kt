package com.hadi.repositoriesimpl

import com.hadi.datasource.CurrencyConverterDataSource
import com.hadi.model.DataResult
import com.hadi.model.HistoricalData
import com.hadi.model.HistoricalDataRequest
import com.hadi.model.LatestCurrencies
import com.hadi.repositories.CurrencyConverterRepository
import javax.inject.Inject

class CurrencyConverterRepositoryImpl @Inject constructor(
    private val currencyConverterDataSource: CurrencyConverterDataSource
) : CurrencyConverterRepository {
    override suspend fun fetchCurrencyRates(base: String?): DataResult<LatestCurrencies> {
        return currencyConverterDataSource.fetchCurrencyRates(base = base)
    }

    override suspend fun fetchHistoricalData(request: HistoricalDataRequest): DataResult<HistoricalData> {
        return currencyConverterDataSource.fetchHistoricalData(request)
    }
}