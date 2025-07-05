package com.diatomicsoft.navigation3.network.api

import com.diatomicsoft.navigation3.data.model.ModelUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UsersApiService {

    @GET("/users")
    suspend fun getUsers(): Response<List<ModelUser>>

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<ModelUser>

}