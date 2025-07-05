package com.diatomicsoft.navigation3.data.repository

import com.diatomicsoft.navigation3.data.model.ModelToDo
import com.diatomicsoft.navigation3.domain.repository.ToDoRepository
import com.diatomicsoft.navigation3.local_storage.dao.ToDoDao
import com.diatomicsoft.navigation3.network.api.TodosApiService
import com.diatomicsoft.navigation3.network.resource.NetworkBoundResource
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class ToDoRepositoryImpl(
    private val api: TodosApiService,
    private val dao: ToDoDao,
) : ToDoRepository {

    override suspend fun fetchToDos(): Flow<Resource<List<ModelToDo>>> {
        return object : NetworkBoundResource<List<ModelToDo>, List<ModelToDo>>() {

            override suspend fun loadFromDb(): Flow<List<ModelToDo>> {
                return dao.getAll()
            }

            override fun shouldFetch(data: List<ModelToDo>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun fetchFromNetwork(): List<ModelToDo> {
                val response = api.getTodos()
                if (response.isSuccessful) {
                    return response.body() ?: emptyList()
                } else {
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: List<ModelToDo>) {
                dao.insertAll(item)
            }

            override fun onFetchFailed(throwable: Throwable) {
                throwable.printStackTrace()
            }
        }.asFlow()
    }

}