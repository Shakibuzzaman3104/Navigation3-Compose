package com.diatomicsoft.navigation3.di

import com.diatomicsoft.navigation3.network.api.AlbumsApiService
import com.diatomicsoft.navigation3.network.api.PostsApiService
import com.diatomicsoft.navigation3.network.api.TodosApiService
import com.diatomicsoft.navigation3.network.api.UsersApiService
import com.diatomicsoft.navigation3.network.interceptors.AuthInterceptor
import com.diatomicsoft.navigation3.network.interceptors.ErrorInterceptor
import com.diatomicsoft.navigation3.network.interceptors.NetworkLoggingInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        errorInterceptor: ErrorInterceptor,
        loggingInterceptor: NetworkLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(errorInterceptor)
        .addInterceptor(loggingInterceptor.create())
        .build()

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = "" //ToDo replace with BuildConfig.API_BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrl baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

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