package com.diatomicsoft.navigation3.di

import com.diatomicsoft.feature.album.data.AlbumsRepositoryImpl
import com.diatomicsoft.feature.album.data.ImagesRepositoryImpl
import com.diatomicsoft.feature.todo.data.ToDoRepositoryImpl
import com.diatomicsoft.feature.users.data.UserDetailsRepositoryImpl
import com.diatomicsoft.feature.users.data.UsersRepositoryImpl
import com.diatomicsoft.feature.album.domain.AlbumsRepository
import com.diatomicsoft.feature.album.domain.ImagesRepository
import com.diatomicsoft.feature.todo.domain.ToDoRepository
import com.diatomicsoft.feature.users.domain.UserDetailsRepository
import com.diatomicsoft.feature.users.domain.UsersRepository
import com.diatomicsoft.core.database.dao.AlbumDao
import com.diatomicsoft.core.database.dao.PhotoDao
import com.diatomicsoft.core.database.dao.ToDoDao
import com.diatomicsoft.core.database.dao.UserDao
import com.diatomicsoft.core.network.api.AlbumsApiService
import com.diatomicsoft.core.network.api.TodosApiService
import com.diatomicsoft.core.network.api.UsersApiService
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
    fun provideUsersRepository(usersApiService: UsersApiService, userDao: UserDao): UsersRepository = UsersRepositoryImpl(usersApiService, userDao)

    @Provides
    @Singleton
    fun provideUserDetailsRepository(usersApiService: UsersApiService, userDao: UserDao): UserDetailsRepository = UserDetailsRepositoryImpl(usersApiService, userDao)

    @Provides
    @Singleton
    fun provideAlbumsRepository(albumsApiService: AlbumsApiService, albumDao: AlbumDao): AlbumsRepository = AlbumsRepositoryImpl(albumsApiService, albumDao)

    @Provides
    @Singleton
    fun provideImagesRepository(albumsApiService: AlbumsApiService, photoDao: PhotoDao): ImagesRepository = ImagesRepositoryImpl(albumsApiService, photoDao)

    @Provides
    @Singleton
    fun provideToDoRepository(todosApiService: TodosApiService, toDoDao: ToDoDao): ToDoRepository = ToDoRepositoryImpl(todosApiService, toDoDao)

}