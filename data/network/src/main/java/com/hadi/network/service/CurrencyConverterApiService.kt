package com.hadi.network.service

import com.hadi.network.model.GenericResponse
import com.hadi.network.model.response.HistoricalDataApiModel
import com.hadi.network.model.response.LatestCurrenciesApiModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterApiService {
    @GET("fixer/latest")
    suspend fun fetchLatestRate(@Query("base") base: String? = null): GenericResponse<LatestCurrenciesApiModel>

    @GET("fixer/fluctuation")
    suspend fun fetchHistoricalData(
        @Query("base") base: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
    ): GenericResponse<HistoricalDataApiModel>
}