package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.navigation3.data.model.ModelPhoto
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    suspend fun fetchImages(id: Int): Flow<Resource<List<ModelPhoto>>>
}