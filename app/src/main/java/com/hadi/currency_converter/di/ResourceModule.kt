package com.hadi.currency_converter.di

import android.content.Context
import com.hadi.resources.ResProvider
import com.hadi.resourcesimpl.ResourcesProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ResourceModule {
    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context
    ): ResProvider {
        return ResourcesProviderImpl(context)
    }
}