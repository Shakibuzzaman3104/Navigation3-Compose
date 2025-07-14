package com.diatomicsoft.core.database.di

import android.content.Context
import androidx.room.Room
import com.diatomicsoft.core.database.base.BaseDatabase
import com.diatomicsoft.core.database.constants.DatabaseConstants
import com.diatomicsoft.core.database.dao.*
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
    ): BaseDatabase =
        Room
            .databaseBuilder(
                context,
                BaseDatabase::class.java,
                DatabaseConstants.DATABASE_NAME,
            ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: BaseDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun providePostDao(database: BaseDatabase): PostDao = database.postDao()

    @Provides
    @Singleton
    fun provideCommentDao(database: BaseDatabase): CommentDao = database.commentDao()

    @Provides
    @Singleton
    fun provideAlbumDao(database: BaseDatabase): AlbumDao = database.albumDao()

    @Provides
    @Singleton
    fun providePhotoDao(database: BaseDatabase): PhotoDao = database.photoDao()

    @Provides
    @Singleton
    fun provideToDoDao(database: BaseDatabase): ToDoDao = database.toDoDao()
}
