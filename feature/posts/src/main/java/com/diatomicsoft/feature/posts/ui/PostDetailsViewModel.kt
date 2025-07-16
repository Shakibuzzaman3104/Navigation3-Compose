package com.diatomicsoft.feature.posts.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.core.database.entity.ModelComment
import com.diatomicsoft.core.network.resource.Resource
import com.diatomicsoft.feature.posts.domain.PostDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val repository: PostDetailsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(PostDetailsState())
        private set

    private val postId = MutableStateFlow(0)

    private val _effect = MutableSharedFlow<PostDetailsScreenEffect>()
    val effect: SharedFlow<PostDetailsScreenEffect> = _effect

    private val _comments = MutableSharedFlow<Int>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val comments = _comments
        .flatMapLatest { repository.fetchComments(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Resource.Loading<List<ModelComment>>()
        )

    fun fetchComments(postId: Int) {
        this.postId.tryEmit(postId)
        _comments.tryEmit(postId)
    }

    fun onIntent(intent: PostDetailsIntent) {
        when (intent) {
            is PostDetailsIntent.RefreshData -> {
                state = state.copy(isRefreshing = true)
                fetchComments(postId.value)
            }
        }
    }

    fun getParameterPostId(): Int? {
        return savedStateHandle.get<Int>("postId")
    }

    fun getParameterTitle(): String? {
        return savedStateHandle.get<String>("title")
    }

    fun getParameterDescription(): String? {
        return savedStateHandle.get<String>("description")
    }

    init {
        savedStateHandle.keys().forEach { key ->
            Log.d("MyId", "${key}")
        }
        postId.tryEmit(getParameterPostId() ?: 0)

        state = state.copy(
            postTitle = getParameterTitle(),
            postDescription = getParameterDescription()
        )

        viewModelScope.launch {
            comments.collect {
                when (it) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true, error = null)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            isRefreshing = false,
                            comments = it.data ?: emptyList(),
                            error = null
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isRefreshing = false,
                            error = it.message ?: "Unknown error"
                        )
                        _effect.emit(PostDetailsScreenEffect.ShowSnackBar(it.message ?: "Error"))
                    }
                }
            }
        }

        viewModelScope.launch {
            postId.collect {
                if (it != 0) {
                    fetchComments(postId.value)
                } else {
                    state = state.copy(
                        isLoading = false,
                        error = "Invalid post ID"
                    )
                }
            }
        }
    }
}