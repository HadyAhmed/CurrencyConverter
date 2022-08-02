package com.hadi.usecase

import com.hadi.model.DataResult
import com.hadi.model.HistoricalData
import com.hadi.model.HistoricalDataRequest

interface FetchHistoricalDataUseCase {
    suspend operator fun invoke(request: HistoricalDataRequest): DataResult<HistoricalData>
}