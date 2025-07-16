package com.diatomicsoft.feature.todo.ui

import com.diatomicsoft.core.database.entity.ModelToDo

sealed class ToDoState {
    object Loading : ToDoState()
    data class Success(val todos: List<ModelToDo>?) : ToDoState()
    data class Error(val message: String) : ToDoState()
}