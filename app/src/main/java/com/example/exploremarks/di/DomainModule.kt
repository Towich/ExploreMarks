package com.example.exploremarks.di

import com.example.exploremarks.data.repository.IMainRepository
import com.example.exploremarks.domain.GetMarksUseCase
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
    fun provideGetMarksUseCase(repository: IMainRepository): GetMarksUseCase {
        return GetMarksUseCase(repository)
    }
}