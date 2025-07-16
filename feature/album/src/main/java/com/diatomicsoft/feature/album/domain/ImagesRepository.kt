package com.diatomicsoft.feature.album.domain

import com.diatomicsoft.core.database.entity.ModelPhoto
import com.diatomicsoft.core.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    fun fetchImages(albumId: Int): Flow<Resource<List<ModelPhoto>>>
}