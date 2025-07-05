package com.diatomicsoft.navigation3.data.repository

import com.diatomicsoft.navigation3.data.model.ModelUser
import com.diatomicsoft.navigation3.domain.repository.UserDetailsRepository
import com.diatomicsoft.navigation3.local_storage.dao.UserDao
import com.diatomicsoft.navigation3.network.api.UsersApiService
import com.diatomicsoft.navigation3.network.resource.NetworkBoundResource
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserDetailsRepositoryImpl(
    private val api: UsersApiService,
    private val dao: UserDao,
) : UserDetailsRepository {

    override suspend fun fetchUserDetails(id: Int): Flow<Resource<ModelUser?>> {
        return object : NetworkBoundResource<ModelUser?, ModelUser?>() {
            override suspend fun loadFromDb(): Flow<ModelUser?> {
                return dao.getUser(id)
            }

            override suspend fun fetchFromNetwork(): ModelUser? {
                val response = api.getUser(id)
                if (response.isSuccessful) {
                    return response.body()
                } else {
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: ModelUser?) {
                if (item != null) {
                    dao.insertUser(item)
                }
            }

            override fun shouldFetch(data: ModelUser?): Boolean {
                return data == null || data.name.isEmpty()
            }
        }.asFlow()
    }

}