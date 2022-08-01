package com.hadi.datasourceimpl

import com.hadi.datasource.CurrencyConverterDataSource
import com.hadi.network.service.CurrencyConverterApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyConverterDataSourceImpl @Inject constructor(
    private val currencyApiService: CurrencyConverterApiService
) : CurrencyConverterDataSource {
    override suspend fun fetchCurrencyRates(): List<String> {
        return currencyApiService.fetchLatestRate()
    }
}