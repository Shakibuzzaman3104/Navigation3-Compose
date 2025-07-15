package com.diatomicsoft.navigation3.ui.screens.albums

import com.diatomicsoft.core.database.entity.ModelPhoto

sealed class ImagesState {
    object Loading : ImagesState()
    data class Success(val images: List<ModelPhoto>?) : ImagesState()
    data class Error(val message: String) : ImagesState()
}