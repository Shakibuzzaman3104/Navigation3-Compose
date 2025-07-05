package com.diatomicsoft.navigation3.network.api

import com.diatomicsoft.navigation3.data.model.ModelAlbum
import com.diatomicsoft.navigation3.data.model.ModelPhoto
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