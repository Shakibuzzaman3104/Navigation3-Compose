package com.diatomicsoft.navigation3.di

import com.diatomicsoft.feature.posts.data.PostDetailsRepositoryImpl
import com.diatomicsoft.feature.posts.data.PostsRepositoryImpl
import com.diatomicsoft.feature.posts.domain.PostDetailsRepository
import com.diatomicsoft.feature.posts.domain.PostsRepository
import com.diatomicsoft.core.database.dao.CommentDao
import com.diatomicsoft.core.database.dao.PostDao
import com.diatomicsoft.core.network.api.PostsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {

    @Provides
    fun provideLocalPostsRepository(
        api: PostsApiService,
        dao: PostDao
    ): PostsRepository =
        PostsRepositoryImpl(api, dao)

    @Provides
    fun providePostDetailsRepository(
        api: PostsApiService,
        dao: CommentDao
    ): PostDetailsRepository =
        PostDetailsRepositoryImpl(api, dao)

}