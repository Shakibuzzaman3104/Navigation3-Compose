package com.diatomicsoft.feature.users.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.feature.users.domain.UserDetailsRepository
import com.diatomicsoft.core.network.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val repo: UserDetailsRepository) : ViewModel() {
    private val _userDetailsState = MutableStateFlow<UserDetailsState>(UserDetailsState.Loading)
    val userDetailsState = _userDetailsState.asStateFlow()

    fun getUserDetails(id: Int) {
        viewModelScope.launch {
            try {
                repo.fetchUserDetails(id).catch { throwable ->
                    // Catch any exceptions in the flow and emit error state
                    _userDetailsState.value = UserDetailsState.Error(throwable.message ?: "An unexpected error occurred")
                }.collectLatest {
                    when (it) {
                        is Resource.Loading -> _userDetailsState.value = UserDetailsState.Loading
                        is Resource.Success -> _userDetailsState.value = UserDetailsState.Success(it.data)
                        is Resource.Error -> _userDetailsState.value = UserDetailsState.Error(it.message ?: "An unexpected error occurred")
                    }
                }
            } catch (e: Exception) {
                _userDetailsState.value = UserDetailsState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

}