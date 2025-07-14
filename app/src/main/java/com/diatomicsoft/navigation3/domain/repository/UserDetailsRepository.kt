package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.core.database.entity.ModelUser
import com.diatomicsoft.core.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UserDetailsRepository {
    suspend fun fetchUserDetails(id: Int): Flow<Resource<ModelUser?>>
}