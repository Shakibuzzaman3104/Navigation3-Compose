package com.diatomicsoft.core.network.api

import com.diatomicsoft.core.database.entity.ModelAlbum
import com.diatomicsoft.core.database.entity.ModelPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumsApiService {

    @GET("/albums")
    suspend fun getAlbums(): Response<List<ModelAlbum>>

    @GET("/photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): Response<List<ModelPhoto>>

}