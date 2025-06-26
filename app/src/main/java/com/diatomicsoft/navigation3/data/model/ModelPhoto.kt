package com.diatomicsoft.navigation3.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "photos")
@JsonClass(generateAdapter = true)
data class ModelPhoto(
    @param:Json(name = "albumId")
    val albumId: Int,
    @PrimaryKey
    @param:Json(name = "id")
    val id: Int,
    @param:Json(name = "thumbnailUrl")
    val thumbnailUrl: String,
    @param:Json(name = "title")
    val title: String,
    @param:Json(name = "url")
    val url: String
)