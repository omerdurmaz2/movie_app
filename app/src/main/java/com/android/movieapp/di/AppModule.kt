package com.android.movieapp.di

import com.android.movieapp.service.RestControllerFactory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun getRestController(): RestControllerFactory = RestControllerFactory()


    @Provides
    @Singleton
    fun getGson(): Gson = Gson()
}