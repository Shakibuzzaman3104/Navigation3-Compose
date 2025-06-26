package com.diatomicsoft.navigation3.network.api

import com.diatomicsoft.navigation3.data.model.ModelComment
import com.diatomicsoft.navigation3.data.model.ModelPost
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApiService {

    @GET("/posts")
    fun getPosts(): Response<List<ModelPost>>

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: Int): Response<ModelPost>

    @GET("/comments")
    fun getComments(@Query("postId") postId: Int): Response<List<ModelComment>>

}