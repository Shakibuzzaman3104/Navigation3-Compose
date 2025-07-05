package com.diatomicsoft.navigation3.ui.screens.albums

import com.diatomicsoft.navigation3.data.model.ModelAlbum

sealed class AlbumsState {
    object Loading : AlbumsState()
    data class Success(val albums: List<ModelAlbum>?) : AlbumsState()
    data class Error(val message: String) : AlbumsState()
}