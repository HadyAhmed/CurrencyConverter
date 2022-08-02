package com.hadi.usecaseimpl

import com.hadi.model.DataResult
import com.hadi.model.HistoricalData
import com.hadi.model.HistoricalDataRequest
import com.hadi.repositories.CurrencyConverterRepository
import com.hadi.usecase.FetchHistoricalDataUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchHistoricalDataUseCaseImpl @Inject constructor(
    private val repository: CurrencyConverterRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : FetchHistoricalDataUseCase {
    override suspend fun invoke(request: HistoricalDataRequest): DataResult<HistoricalData> =
        withContext(defaultDispatcher) {
            repository.fetchHistoricalData(request = request)
        }
}