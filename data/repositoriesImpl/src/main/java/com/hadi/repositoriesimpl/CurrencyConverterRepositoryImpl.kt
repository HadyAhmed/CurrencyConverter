package com.hadi.repositoriesimpl

import com.hadi.datasource.CurrencyConverterDataSource
import com.hadi.repositories.CurrencyConverterRepository
import javax.inject.Inject

class CurrencyConverterRepositoryImpl @Inject constructor(
    private val currencyConverterDataSource: CurrencyConverterDataSource
) : CurrencyConverterRepository {
    override suspend fun fetchCurrencyRates(): List<String> {
        return currencyConverterDataSource.fetchCurrencyRates()
    }
}