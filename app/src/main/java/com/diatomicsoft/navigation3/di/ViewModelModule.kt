package com.diatomicsoft.navigation3.di

import com.diatomicsoft.navigation3.data.repository.PostsRepositoryImpl
import com.diatomicsoft.navigation3.domain.repository.PostsRepository
import com.diatomicsoft.navigation3.local_storage.dao.PostDao
import com.diatomicsoft.navigation3.network.api.PostsApiService
import com.diatomicsoft.navigation3.network.utils.NetworkMonitor
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

}