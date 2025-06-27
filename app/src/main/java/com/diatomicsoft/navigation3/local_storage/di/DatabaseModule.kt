package com.diatomicsoft.navigation3.local_storage.di

import android.content.Context
import com.diatomicsoft.navigation3.local_storage.database.JsonPlaceholderDatabase
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

    @Provides
    @Singleton
    fun provideUserDao(database: JsonPlaceholderDatabase) = database.userDao()

    @Provides
    @Singleton
    fun providePostDao(database: JsonPlaceholderDatabase) = database.postDao()

    @Provides
    @Singleton
    fun provideCommentDao(database: JsonPlaceholderDatabase) = database.commentDao()

    @Provides
    @Singleton
    fun provideAlbumDao(database: JsonPlaceholderDatabase) = database.albumDao()

    @Provides
    @Singleton
    fun providePhotoDao(database: JsonPlaceholderDatabase) = database.photoDao()

    @Provides
    @Singleton
    fun provideToDoDao(database: JsonPlaceholderDatabase) = database.toDoDao()

}
