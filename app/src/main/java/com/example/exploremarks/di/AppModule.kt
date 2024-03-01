package com.example.exploremarks.di

import android.app.Application
import com.example.exploremarks.data.repository.MainRepository
import com.example.exploremarks.data.repository.IMainRepository
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
    fun provideMainRepository(app: Application): IMainRepository {
        return MainRepository(appContext = app)
    }
}