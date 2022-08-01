package com.hadi.usecase

interface FetchLatestRateUseCase {
    suspend operator fun invoke(): List<String>
}