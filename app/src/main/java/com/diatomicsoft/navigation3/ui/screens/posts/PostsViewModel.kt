package com.diatomicsoft.navigation3.ui.screens.posts

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.navigation3.domain.repository.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val repository: PostsRepository) : ViewModel() {

    var postState by mutableStateOf(PostsState())
        private set

    init {

        viewModelScope.launch {
            postState = postState.copy(
                isPostsLoading = true
            )
            repository.fetchPosts().collect {
                postState = postState.copy(
                    isPostsLoading = false,
                    posts = it
                )

            }
        }
    }

}