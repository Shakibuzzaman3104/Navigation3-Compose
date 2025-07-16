package com.diatomicsoft.feature.todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.feature.todo.domain.ToDoRepository
import com.diatomicsoft.core.network.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(private val repo: ToDoRepository) : ViewModel() {
    private val _toDoState = MutableStateFlow<ToDoState>(ToDoState.Loading)
    val toDoState = _toDoState.asStateFlow()

    fun getToDos() {
        viewModelScope.launch {
            try {
                repo.fetchToDos().collectLatest {
                    when (it) {
                        is Resource.Loading -> _toDoState.value = ToDoState.Loading
                        is Resource.Success -> _toDoState.value = ToDoState.Success(it.data ?: emptyList())
                        is Resource.Error -> _toDoState.value = ToDoState.Error(it.message ?: "An unexpected error occurred")
                    }
                }
            } catch (e: Exception) {
                _toDoState.value = ToDoState.Error(e.message ?: "An unexpected error occurred")
            }
        }
    }

}