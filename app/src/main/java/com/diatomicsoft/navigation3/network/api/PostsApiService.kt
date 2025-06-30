package com.diatomicsoft.navigation3.network.api

import com.diatomicsoft.navigation3.data.model.ModelComment
import com.diatomicsoft.navigation3.data.model.ModelPost
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<ModelPost>>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): Response<ModelPost>

    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): Response<List<ModelComment>>

    @GET("comments/{commentId}")
    suspend fun getComment(@Path("commentId") commentId: Int): Response<ModelComment>

}