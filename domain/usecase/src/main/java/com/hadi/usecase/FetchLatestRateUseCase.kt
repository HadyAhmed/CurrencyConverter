package com.hadi.usecase

import com.hadi.model.DataResult
import com.hadi.model.LatestCurrencies

interface FetchLatestRateUseCase {
    suspend operator fun invoke(base: String? = null): DataResult<LatestCurrencies>
}