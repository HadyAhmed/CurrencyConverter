package com.hadi.usecaseimpl

import com.hadi.model.DataResult
import com.hadi.model.LatestCurrencies
import com.hadi.repositories.CurrencyConverterRepository
import com.hadi.usecase.FetchLatestRateUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchLatestRateUseCaseImpl @Inject constructor(
    private val repository: CurrencyConverterRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : FetchLatestRateUseCase {
    override suspend fun invoke(base: String?): DataResult<LatestCurrencies> =
        withContext(defaultDispatcher) {
            repository.fetchCurrencyRates(base = base)
        }
}