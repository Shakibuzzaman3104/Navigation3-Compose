package com.diatomicsoft.feature.todo.data

import com.diatomicsoft.core.database.dao.ToDoDao
import com.diatomicsoft.core.database.entity.ModelToDo
import com.diatomicsoft.core.network.api.TodosApiService
import com.diatomicsoft.core.network.resource.NetworkBoundResource
import com.diatomicsoft.core.network.resource.Resource
import com.diatomicsoft.feature.todo.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
    private val api: TodosApiService,
    private val dao: ToDoDao
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