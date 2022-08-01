package com.hadi.currency_converter.di.usecase

import com.hadi.usecase.FetchLatestRateUseCase
import com.hadi.usecaseimpl.FetchLatestRateUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CurrencyUseCaseModule {
    @Binds
    fun bindGetBillServicesUseCase(
        impl: FetchLatestRateUseCaseImpl
    ): FetchLatestRateUseCase
}