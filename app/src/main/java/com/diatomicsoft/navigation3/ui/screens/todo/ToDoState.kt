package com.diatomicsoft.navigation3.ui.screens.todo

import com.diatomicsoft.core.database.entity.ModelToDo

sealed class ToDoState {
    object Loading : ToDoState()
    data class Success(val todos: List<ModelToDo>?) : ToDoState()
    data class Error(val message: String) : ToDoState()
}