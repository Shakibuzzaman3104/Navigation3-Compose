package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.navigation3.data.model.ModelUser
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface UserDetailsRepository {
    suspend fun fetchUserDetails(id: Int): Flow<Resource<ModelUser?>>
}