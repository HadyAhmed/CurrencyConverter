package com.hadi.usecase

import com.hadi.model.LatestCurrencies

interface FetchLatestRateUseCase {
    suspend operator fun invoke(base: String? = null): LatestCurrencies
}