package com.diatomicsoft.navigation3.ui.screens.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.navigation3.domain.repository.AlbumsRepository
import com.diatomicsoft.core.network.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumsViewModel @Inject constructor(private val repo: AlbumsRepository) : ViewModel() {
    private val _albumsState = MutableStateFlow<AlbumsState>(AlbumsState.Loading)
    val albumsState = _albumsState.asStateFlow()

    fun getAlbums() {
        viewModelScope.launch {
            repo.fetchAlbums().collectLatest {
                when (it) {
                    is Resource.Loading -> _albumsState.value = AlbumsState.Loading
                    is Resource.Success -> _albumsState.value = AlbumsState.Success(it.data ?: emptyList())
                    is Resource.Error -> _albumsState.value = AlbumsState.Error(it.message ?: "An unexpected error occurred")
                }
            }
        }
    }

}