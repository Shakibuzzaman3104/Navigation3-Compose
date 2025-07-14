package com.diatomicsoft.navigation3.di

import com.diatomicsoft.core.network.api.AlbumsApiService
import com.diatomicsoft.core.network.api.PostsApiService
import com.diatomicsoft.core.network.api.TodosApiService
import com.diatomicsoft.core.network.api.UsersApiService
import com.diatomicsoft.core.network.di.BaseUrl
import com.diatomicsoft.core.network.interceptors.AuthInterceptor
import com.diatomicsoft.core.network.interceptors.NetworkLoggingInterceptor
import com.diatomicsoft.navigation3.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppNetworkModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = BASE_URL //ToDo replace with BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideAlbumsApiService(retrofit: Retrofit): AlbumsApiService =
        retrofit.create(AlbumsApiService::class.java)

    @Provides
    @Singleton
    fun providePostsApiService(retrofit: Retrofit): PostsApiService =
        retrofit.create(PostsApiService::class.java)

    @Provides
    @Singleton
    fun provideToDoApiService(retrofit: Retrofit): TodosApiService =
        retrofit.create(TodosApiService::class.java)

    @Provides
    @Singleton
    fun provideUsersApiService(retrofit: Retrofit): UsersApiService =
        retrofit.create(UsersApiService::class.java)


}