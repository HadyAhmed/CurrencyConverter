package com.hadi.usecase

import com.hadi.model.LatestCurrencies

interface FetchLatestRateUseCase {
    suspend operator fun invoke(): LatestCurrencies
}