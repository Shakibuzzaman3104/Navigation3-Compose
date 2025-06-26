package com.diatomicsoft.navigation3.local_storage.di

import android.content.Context
import com.diatomicsoft.navigation3.local_storage.base.JsonPlaceholderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): JsonPlaceholderDatabase = JsonPlaceholderDatabase.init(context)
}
