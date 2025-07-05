package com.diatomicsoft.navigation3.ui.screens.todo

import com.diatomicsoft.navigation3.data.model.ModelToDo

sealed class ToDoState {
    object Loading : ToDoState()
    data class Success(val todos: List<ModelToDo>?) : ToDoState()
    data class Error(val message: String) : ToDoState()
}