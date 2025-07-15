package com.diatomicsoft.navigation3.ui.screens.albums

import com.diatomicsoft.core.database.entity.ModelAlbum

sealed class AlbumsState {
    object Loading : AlbumsState()
    data class Success(val albums: List<ModelAlbum>?) : AlbumsState()
    data class Error(val message: String) : AlbumsState()
}