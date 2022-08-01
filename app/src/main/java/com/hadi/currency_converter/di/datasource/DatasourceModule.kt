package com.hadi.currency_converter.di.datasource

import com.hadi.datasource.CurrencyConverterDataSource
import com.hadi.datasourceimpl.CurrencyConverterDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class, SingletonComponent::class)
interface DatasourceModule {
    @Binds
    fun bindCurrencyConverterRepository(
        impl: CurrencyConverterDataSourceImpl
    ): CurrencyConverterDataSource
}