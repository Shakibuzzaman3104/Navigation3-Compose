package com.diatomicsoft.navigation3.data.repository

import com.diatomicsoft.navigation3.data.model.ModelUser
import com.diatomicsoft.navigation3.domain.repository.UsersRepository
import com.diatomicsoft.navigation3.local_storage.dao.UserDao
import com.diatomicsoft.navigation3.network.api.UsersApiService
import com.diatomicsoft.navigation3.network.resource.NetworkBoundResource
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UsersRepositoryImpl(
    private val api: UsersApiService,
    private val dao: UserDao,
) : UsersRepository {

    override suspend fun fetchUsers(): Flow<Resource<List<ModelUser>>> {
        return object : NetworkBoundResource<List<ModelUser>, List<ModelUser>>() {

            override suspend fun loadFromDb(): Flow<List<ModelUser>> {
                return dao.getUsers()
            }

            override fun shouldFetch(data: List<ModelUser>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun fetchFromNetwork(): List<ModelUser> {
                val response = api.getUsers()
                if (response.isSuccessful) {
                    return response.body() ?: emptyList()
                } else {
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: List<ModelUser>) {
                dao.insertUsers(item)
            }

            override fun onFetchFailed(throwable: Throwable) {
                throwable.printStackTrace()
            }
        }.asFlow()
    }

}