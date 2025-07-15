package com.diatomicsoft.core.network.api

import com.diatomicsoft.core.database.entity.ModelToDo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TodosApiService {

    @GET("/todos")
    suspend fun getTodos(): Response<List<ModelToDo>>

}