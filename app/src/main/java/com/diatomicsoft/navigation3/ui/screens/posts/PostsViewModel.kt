package com.diatomicsoft.navigation3.ui.screens.posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.navigation3.data.model.ModelPost
import com.diatomicsoft.navigation3.domain.repository.PostsRepository
import com.diatomicsoft.navigation3.network.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val repository: PostsRepository) : ViewModel() {

    var postState by mutableStateOf(PostsState())
        private set

    private val _posts = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val posts = _posts
        .onStart { repository.fetchPosts() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Resource.Loading<List<ModelPost>>()
        )

    fun getPosts() {
        _posts.tryEmit(Unit)
    }

    init {
        viewModelScope.launch {
            postState = postState.copy(
                isPostsLoading = true
            )
            repository.fetchPosts().collect {
                when (it) {
                    is Resource.Error<List<ModelPost>> -> {}
                    is Resource.Loading<List<ModelPost>> -> {}
                    is Resource.Success<List<ModelPost>> -> {}
                }
            }
        }

    }
}