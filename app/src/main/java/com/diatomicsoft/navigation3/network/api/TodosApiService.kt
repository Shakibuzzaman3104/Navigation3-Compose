package com.diatomicsoft.navigation3.network.api

import com.diatomicsoft.navigation3.data.model.ModelPost
import com.diatomicsoft.navigation3.data.model.ModelToDo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TodosApiService {

    @GET("/todos")
    fun getTodos(): Response<List<ModelToDo>>

    @GET("/todos/{id}")
    fun getTodo(@Path("id") id: Int): Response<List<ModelToDo>>


}