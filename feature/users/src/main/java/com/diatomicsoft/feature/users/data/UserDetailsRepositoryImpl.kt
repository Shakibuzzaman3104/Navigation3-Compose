package com.diatomicsoft.feature.users.data

import com.diatomicsoft.core.database.dao.UserDao
import com.diatomicsoft.core.database.entity.ModelUser
import com.diatomicsoft.core.network.api.UsersApiService
import com.diatomicsoft.core.network.resource.NetworkBoundResource
import com.diatomicsoft.core.network.resource.Resource
import com.diatomicsoft.feature.users.domain.UserDetailsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class UserDetailsRepositoryImpl @Inject constructor(
    private val api: UsersApiService,
    private val dao: UserDao
) : UserDetailsRepository {

    override suspend fun fetchUserDetails(userId: Int): Flow<Resource<ModelUser?>> {
        return object : NetworkBoundResource<ModelUser?, ModelUser>() {

            override suspend fun loadFromDb(): Flow<ModelUser?> {
                return dao.getUser(userId)
            }

            override fun shouldFetch(data: ModelUser?): Boolean {
                return data == null
            }

            override suspend fun fetchFromNetwork(): ModelUser {
                val response = api.getUser(userId)
                if (response.isSuccessful) {
                    return response.body() ?: throw Exception("User not found")
                } else {
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: ModelUser) {
                dao.insertUser(item)
            }

            override fun onFetchFailed(throwable: Throwable) {
                throwable.printStackTrace()
            }
        }.asFlow()
    }
}