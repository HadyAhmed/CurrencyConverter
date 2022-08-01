package com.hadi.network.service

import com.hadi.network.model.LatestCurrenciesApiModel
import retrofit2.http.GET

interface CurrencyConverterApiService {
    @GET("fixer/latest")
    suspend fun fetchLatestRate(): LatestCurrenciesApiModel
}