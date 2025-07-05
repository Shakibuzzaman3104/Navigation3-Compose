package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.navigation3.data.model.ModelAlbum
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface AlbumsRepository {
    suspend fun fetchAlbums(): Flow<Resource<List<ModelAlbum>>>
}