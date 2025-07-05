package com.diatomicsoft.navigation3.network.api

import com.diatomicsoft.navigation3.data.model.ModelToDo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TodosApiService {

    @GET("/todos")
    suspend fun getTodos(): Response<List<ModelToDo>>
/*
    @GET("/todos/{id}")
    suspend fun getTodo(@Path("id") id: Int): Response<List<ModelToDo>>*/


}