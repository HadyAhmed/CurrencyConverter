package com.hadi.currency_converter.di.network

import com.hadi.network.service.CurrencyConverterApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {
    @Provides
    fun provideHomeService(retrofit: Retrofit): CurrencyConverterApiService =
        retrofit.create(CurrencyConverterApiService::class.java)
}