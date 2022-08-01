package com.hadi.network.service

import retrofit2.http.GET

interface CurrencyConverterApiService {
    @GET("fixer/latest")
    suspend fun fetchLatestRate(): List<String>
}