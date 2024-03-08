package com.example.exploremarks.di

import com.example.exploremarks.data.repository.AuthorizationRepository
import com.example.exploremarks.data.repository.MainRepository
import com.example.exploremarks.domain.ClearUserDataUseCase
import com.example.exploremarks.domain.CreateMarkUseCase
import com.example.exploremarks.domain.DeleteMarkUseCase
import com.example.exploremarks.domain.DislikeMarkUseCase
import com.example.exploremarks.domain.GetMarksUseCase
import com.example.exploremarks.domain.GetUserDataUseCase
import com.example.exploremarks.domain.LikeMarkUseCase
import com.example.exploremarks.domain.LoginUseCase
import com.example.exploremarks.domain.RegisterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    // MainRepository

    @Provides
    @Singleton
    fun provideGetMarksUseCase(repository: MainRepository): GetMarksUseCase {
        return GetMarksUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideGetUserDataUseCase(repository: MainRepository): GetUserDataUseCase {
        return GetUserDataUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideClearUserDataUseCase(repository: MainRepository): ClearUserDataUseCase {
        return ClearUserDataUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideLikeMarkUseCase(repository: MainRepository): LikeMarkUseCase {
        return LikeMarkUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideDislikeMarkUseCase(repository: MainRepository): DislikeMarkUseCase {
        return DislikeMarkUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideCreateMarkUseCase(repository: MainRepository): CreateMarkUseCase {
        return CreateMarkUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideDeleteMarkUseCase(repository: MainRepository): DeleteMarkUseCase {
        return DeleteMarkUseCase(repository)
    }


    // AuthorizationRepository

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: AuthorizationRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRegisterUseCase(repository: AuthorizationRepository): RegisterUseCase {
        return RegisterUseCase(repository)
    }


}