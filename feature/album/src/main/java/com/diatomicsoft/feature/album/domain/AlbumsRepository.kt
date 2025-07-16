package com.diatomicsoft.feature.album.domain

import com.diatomicsoft.core.database.entity.ModelAlbum
import com.diatomicsoft.core.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    suspend fun fetchAlbums(): Flow<Resource<List<ModelAlbum>>>
}