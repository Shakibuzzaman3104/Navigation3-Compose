package com.diatomicsoft.navigation3.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.navigation3.domain.repository.UserDetailsRepository
import com.diatomicsoft.navigation3.network.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val repo: UserDetailsRepository) : ViewModel() {
    private val _userDetailsState = MutableStateFlow<UserDetailsState>(UserDetailsState.Loading)
    val userDetailsState = _userDetailsState.asStateFlow()

    fun getUserDetails(id: Int) {
        viewModelScope.launch {
            repo.fetchUserDetails(id).collectLatest {
                when (it) {
                    is Resource.Loading -> _userDetailsState.value = UserDetailsState.Loading
                    is Resource.Success -> _userDetailsState.value = UserDetailsState.Success(it.data)
                    is Resource.Error -> _userDetailsState.value = UserDetailsState.Error(it.message ?: "An unexpected error occurred")
                }
            }
        }
    }

}