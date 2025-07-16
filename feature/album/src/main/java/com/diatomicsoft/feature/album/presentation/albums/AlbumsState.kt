package com.diatomicsoft.feature.album.presentation.albums

import com.diatomicsoft.core.database.entity.ModelAlbum

sealed class AlbumsState {
    object Loading : AlbumsState()
    data class Success(val albums: List<ModelAlbum>?) : AlbumsState()
    data class Error(val message: String) : AlbumsState()
}