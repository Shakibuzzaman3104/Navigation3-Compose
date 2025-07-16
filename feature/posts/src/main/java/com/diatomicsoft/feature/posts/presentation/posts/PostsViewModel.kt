package com.diatomicsoft.feature.posts.presentation.posts

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.core.network.resource.Resource
import com.diatomicsoft.feature.posts.domain.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val repository: PostsRepository
) : ViewModel() {

    var postState by mutableStateOf(PostsState())
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
            initialValue = Resource.Loading()
        )

    fun onIntent(intent: PostsIntent) {
        when (intent) {
            PostsIntent.RefreshData -> {
                refreshPosts()
            }

            is PostsIntent.UpdateSearchQuery -> {
                updateSearchQuery(intent.query)
            }
        }
    }

    fun updateSearchQuery(query: String) {
        postState = postState.copy(searchQuery = query)
        filterPosts(query)
    }

    private fun filterPosts(query: String) {
        postState = postState.copy(
            filteredPosts = postState.posts.filter { post ->
                post.title.contains(query, ignoreCase = true) ||
                        post.body.contains(query, ignoreCase = true)
            }
        )
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
