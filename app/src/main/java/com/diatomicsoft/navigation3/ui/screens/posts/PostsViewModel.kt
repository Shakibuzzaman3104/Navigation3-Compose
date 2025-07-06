package com.diatomicsoft.navigation3.ui.screens.posts

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.navigation3.data.model.ModelPost
import com.diatomicsoft.navigation3.domain.repository.PostsRepository
import com.diatomicsoft.navigation3.network.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModelEnhanced @Inject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    var postState by mutableStateOf(PostsStateEnhanced())
        private set
    
    var searchQuery by mutableStateOf("")
        private set

    private val _posts = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    private val posts = _posts
        .onStart { emit(Unit) }
        .flatMapLatest { repository.fetchPosts() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Resource.Loading<List<ModelPost>>()
        )

    // Search functionality
    @OptIn(FlowPreview::class)
    val filteredPosts: List<ModelPost> by derivedStateOf {
        if (searchQuery.isBlank()) {
            postState.posts
        } else {
            postState.posts.filter { post ->
                post.title.contains(searchQuery, ignoreCase = true) ||
                post.body.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun fetchPosts() {
        postState = postState.copy(isLoading = true, error = null)
        _posts.tryEmit(Unit)
    }

    fun refreshPosts() {
        postState = postState.copy(isRefreshing = true, error = null)
        _posts.tryEmit(Unit)
    }

    init {
        viewModelScope.launch {
            posts.collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        postState = postState.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = resource.message ?: "Unknown error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        if (!postState.isRefreshing) {
                            postState = postState.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        postState = postState.copy(
                            isLoading = false,
                            isRefreshing = false,
                            posts = resource.data ?: emptyList(),
                            error = null
                        )
                    }
                }
            }
        }
    }
}

data class PostsStateEnhanced(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val posts: List<ModelPost> = emptyList(),
    val error: String? = null
)
