package com.hadi.currency_converter.di.dispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Singleton
    @Provides
    fun bindCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default
}