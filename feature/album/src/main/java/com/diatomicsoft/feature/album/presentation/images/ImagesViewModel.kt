package com.diatomicsoft.feature.album.presentation.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.core.network.resource.Resource
import com.diatomicsoft.feature.album.domain.ImagesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(private val repo: ImagesRepository) : ViewModel() {
    private val _imagesState = MutableStateFlow<ImagesState>(ImagesState.Loading)
    val imagesState = _imagesState.asStateFlow()

    fun getImages(id: Int) {
        viewModelScope.launch {
            repo.fetchImages(id).collectLatest {
                when (it) {
                    is Resource.Loading -> _imagesState.value = ImagesState.Loading
                    is Resource.Success -> _imagesState.value = ImagesState.Success(it.data ?: emptyList())
                    is Resource.Error -> _imagesState.value = ImagesState.Error(it.message ?: "An unexpected error occurred")
                }
            }
        }
    }

}