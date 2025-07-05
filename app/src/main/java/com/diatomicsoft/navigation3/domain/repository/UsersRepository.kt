package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.navigation3.data.model.ModelUser
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun fetchUsers(): Flow<Resource<List<ModelUser>>>
}