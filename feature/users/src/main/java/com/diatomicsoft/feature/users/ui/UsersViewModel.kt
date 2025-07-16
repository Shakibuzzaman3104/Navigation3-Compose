package com.diatomicsoft.feature.users.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.feature.users.domain.UsersRepository
import com.diatomicsoft.core.network.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repo: UsersRepository) : ViewModel() {
    private val _usersState = MutableStateFlow<UsersState>(UsersState.Loading)
    val usersState = _usersState.asStateFlow()

    fun getUsers() {
        viewModelScope.launch {
            try {
                repo.fetchUsers().catch { throwable ->
                    // Catch any exceptions in the flow and emit error state
                    _usersState.value = UsersState.Error(throwable.message ?: "An unexpected error occurred")
                }.collectLatest {
                    when (it) {
                        is Resource.Loading -> _usersState.value = UsersState.Loading
                        is Resource.Success -> _usersState.value = UsersState.Success(it.data ?: emptyList())
                        is Resource.Error -> _usersState.value = UsersState.Error(it.message ?: "An unexpected error occurred")
                    }
                }
            } catch (e: Exception) {
                _usersState.value = UsersState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

}