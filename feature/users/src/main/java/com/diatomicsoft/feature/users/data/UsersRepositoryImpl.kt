package com.diatomicsoft.feature.users.data

import com.diatomicsoft.core.database.dao.UserDao
import com.diatomicsoft.core.database.entity.ModelUser
import com.diatomicsoft.core.network.api.UsersApiService
import com.diatomicsoft.core.network.resource.NetworkBoundResource
import com.diatomicsoft.core.network.resource.Resource
import com.diatomicsoft.feature.users.domain.UsersRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val api: UsersApiService,
    private val dao: UserDao
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