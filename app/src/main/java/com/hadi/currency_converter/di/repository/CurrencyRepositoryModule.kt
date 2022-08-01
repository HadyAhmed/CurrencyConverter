package com.hadi.currency_converter.di.repository

import com.hadi.repositories.CurrencyConverterRepository
import com.hadi.repositoriesimpl.CurrencyConverterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
interface CurrencyRepositoryModule {
    @Binds
    fun bindCurrencyConverterRepository(
        impl: CurrencyConverterRepositoryImpl
    ): CurrencyConverterRepository
}