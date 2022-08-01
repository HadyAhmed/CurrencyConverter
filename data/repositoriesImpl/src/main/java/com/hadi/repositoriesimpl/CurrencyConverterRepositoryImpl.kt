package com.hadi.repositoriesimpl

import com.hadi.datasource.CurrencyConverterDataSource
import com.hadi.model.LatestCurrencies
import com.hadi.repositories.CurrencyConverterRepository
import javax.inject.Inject

class CurrencyConverterRepositoryImpl @Inject constructor(
    private val currencyConverterDataSource: CurrencyConverterDataSource
) : CurrencyConverterRepository {
    override suspend fun fetchCurrencyRates(base: String?): LatestCurrencies {
        return currencyConverterDataSource.fetchCurrencyRates(base = base)
    }
}