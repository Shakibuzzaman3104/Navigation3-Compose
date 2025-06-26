package com.diatomicsoft.navigation3.network.api

import com.diatomicsoft.navigation3.data.model.ModelAlbum
import com.diatomicsoft.navigation3.data.model.ModelPhoto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumsApiService {

    @GET("/albums")
    fun getAlbums(): Response<List<ModelAlbum>>

    @GET("/albums/{id}")
    fun getAlbum(@Path("id") id: Int): Response<ModelAlbum>

    @GET("/photos")
    fun getPhotos(@Query("albumId") albumId: Int): Response<List<ModelPhoto>>

}