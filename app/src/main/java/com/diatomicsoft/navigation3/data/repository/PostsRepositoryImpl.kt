package com.diatomicsoft.navigation3.data.repository

import android.content.Context
import com.diatomicsoft.navigation3.data.model.ModelPost
import com.diatomicsoft.navigation3.domain.repository.PostsRepository
import com.diatomicsoft.navigation3.utils.getDataListFromAssets
import com.squareup.moshi.Moshi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PostsRepositoryImpl(
    private val context: Context,
    private val moshi: Moshi
) : PostsRepository {

    override suspend fun fetchPosts(): Flow<List<ModelPost>> {
        delay(500)
        return flowOf(context.getDataListFromAssets<ModelPost>("", moshi)) //ToDo replace with real implementation
    }

}

