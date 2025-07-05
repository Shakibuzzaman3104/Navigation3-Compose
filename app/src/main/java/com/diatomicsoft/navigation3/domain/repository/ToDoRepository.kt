package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.navigation3.data.model.ModelToDo
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun fetchToDos(): Flow<Resource<List<ModelToDo>>>
}