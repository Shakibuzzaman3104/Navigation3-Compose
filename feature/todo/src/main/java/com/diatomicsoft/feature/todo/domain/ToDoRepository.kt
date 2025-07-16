package com.diatomicsoft.feature.todo.domain

import com.diatomicsoft.core.database.entity.ModelToDo
import com.diatomicsoft.core.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun fetchToDos(): Flow<Resource<List<ModelToDo>>>
}