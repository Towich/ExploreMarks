package com.example.exploremarks.di

import com.example.exploremarks.data.repository.AuthorizationRepository
import com.example.exploremarks.data.repository.MainRepository
import com.example.exploremarks.domain.GetMarksUseCase
import com.example.exploremarks.domain.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetMarksUseCase(repository: MainRepository): GetMarksUseCase {
        return GetMarksUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthorizationRepository): LoginUseCase {
        return LoginUseCase(repository)
    }


}