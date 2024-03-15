package com.example.exploremarks.di

import android.app.Application
import com.example.exploremarks.data.source.AppContentProvider
import com.example.exploremarks.data.source.CacheSession
import com.example.exploremarks.data.source.SharedPref
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
    fun provideMainRepository(
        app: Application,
        apiService: ApiService,
        cacheSession: CacheSession,
        sharedPref: SharedPref,
        appContentProvider: AppContentProvider
    ): MainRepository {
        return MainRepositoryImpl(
            appContext = app,
            apiService = apiService,
            cacheSession = cacheSession,
            sharedPref = sharedPref,
            appContentProvider = appContentProvider
        )
    }

    @Provides
    @Singleton
    fun provideAuthorizationRepository(
        app: Application,
        apiService: ApiService,
        sharedPref: SharedPref,
        cacheSession: CacheSession
    ): AuthorizationRepository {
        return AuthorizationRepositoryImpl(
            appContext = app,
            apiService = apiService,
            sharedPref = sharedPref,
            cacheSession = cacheSession
        )
    }

    @Provides
    @Singleton
    fun provideSharedPref(appContext: Application): SharedPref {
        return SharedPref(appContext)
    }

    @Provides
    @Singleton
    fun provideCacheSession(sharedPref: SharedPref): CacheSession {
        return CacheSession(sharedPref = sharedPref)
    }

    @Provides
    @Singleton
    fun provideAppContentProvider(appContext: Application): AppContentProvider {
        return AppContentProvider(appContext = appContext)
    }
}