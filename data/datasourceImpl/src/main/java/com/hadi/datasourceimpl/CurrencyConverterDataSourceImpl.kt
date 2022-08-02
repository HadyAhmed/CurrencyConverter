package com.hadi.datasourceimpl

import com.hadi.datasource.CurrencyConverterDataSource
import com.hadi.model.DataResult
import com.hadi.model.HistoricalData
import com.hadi.model.HistoricalDataRequest
import com.hadi.model.LatestCurrencies
import com.hadi.network.mapper.toDomain
import com.hadi.network.model.NetworkResponse
import com.hadi.network.service.CurrencyConverterApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyConverterDataSourceImpl @Inject constructor(
    private val currencyApiService: CurrencyConverterApiService
) : CurrencyConverterDataSource {

    override suspend fun fetchCurrencyRates(base: String?): DataResult<LatestCurrencies> {
        return try {
            currencyApiService.fetchLatestRate(base = base).let {
                when (it) {
                    is NetworkResponse.ApiError -> {
                        DataResult.Failure(Throwable(it.body))
                    }
                    is NetworkResponse.NetworkError -> {
                        DataResult.Failure(RuntimeException(it.error.localizedMessage))
                    }
                    is NetworkResponse.Success -> {
                        DataResult.Success(it.body.toDomain())
                    }
                    is NetworkResponse.UnknownError -> {
                        DataResult.Failure(RuntimeException(it.error.localizedMessage))
                    }
                }
            }
        } catch (e: Throwable) {
            DataResult.Failure(RuntimeException(e))
        }
    }

    override suspend fun fetchHistoricalData(request: HistoricalDataRequest): DataResult<HistoricalData> {
        return try {
            currencyApiService.fetchHistoricalData(
                base = request.base,
                startDate = request.startDate,
                endDate = request.endDate,
            ).let {
                when (it) {
                    is NetworkResponse.ApiError -> {
                        DataResult.Failure(Throwable(it.body))
                    }
                    is NetworkResponse.NetworkError -> {
                        DataResult.Failure(RuntimeException(it.error.localizedMessage))
                    }
                    is NetworkResponse.Success -> {
                        DataResult.Success(it.body.toDomain())
                    }
                    is NetworkResponse.UnknownError -> {
                        DataResult.Failure(RuntimeException(it.error.localizedMessage))
                    }
                }
            }
        } catch (e: Throwable) {
            DataResult.Failure(RuntimeException(e))
        }

    }
}