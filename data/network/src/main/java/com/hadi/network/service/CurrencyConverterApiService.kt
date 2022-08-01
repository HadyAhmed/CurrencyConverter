package com.hadi.network.service

import com.hadi.network.model.LatestCurrenciesApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterApiService {
    @GET("fixer/latest")
    suspend fun fetchLatestRate(@Query("base") base: String? = null): LatestCurrenciesApiModel
}