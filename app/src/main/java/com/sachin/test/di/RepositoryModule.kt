package com.sachin.test.di

import com.sachin.test.network.RetrofitService
import com.sachin.test.home.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(retrofitService: RetrofitService) : HomeRepository {
        return HomeRepository(retrofitService)
    }
}