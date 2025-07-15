package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.core.database.entity.ModelPhoto
import com.diatomicsoft.core.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    suspend fun fetchImages(id: Int): Flow<Resource<List<ModelPhoto>>>
}