package com.diatomicsoft.navigation3.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMoshi():Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()



}