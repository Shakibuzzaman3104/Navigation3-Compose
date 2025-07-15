package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.core.database.entity.ModelUser
import com.diatomicsoft.core.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun fetchUsers(): Flow<Resource<List<ModelUser>>>
}