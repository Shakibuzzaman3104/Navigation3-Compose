package com.diatomicsoft.navigation3.di

import android.content.Context
import com.diatomicsoft.navigation3.data.repository.PostsRepositoryImpl
import com.diatomicsoft.navigation3.domain.repository.PostsRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideLocalPostsRepository(@ApplicationContext context: Context, moshi: Moshi): PostsRepository =
        PostsRepositoryImpl(context, moshi)

}