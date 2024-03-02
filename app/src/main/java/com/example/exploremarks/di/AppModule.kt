package com.example.exploremarks.di

import android.app.Application
import com.example.exploremarks.data.repository.AuthorizationRepository
import com.example.exploremarks.data.repository.AuthorizationRepositoryImpl
import com.example.exploremarks.data.repository.MainRepositoryImpl
import com.example.exploremarks.data.repository.MainRepository
import com.example.exploremarks.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMainRepository(app: Application, apiService: ApiService): MainRepository {
        return MainRepositoryImpl(appContext = app, apiService = apiService)
    }

    @Provides
    @Singleton
    fun provideAuthorizationRepository(app: Application, apiService: ApiService): AuthorizationRepository {
        return AuthorizationRepositoryImpl(appContext = app, apiService = apiService)
    }
}