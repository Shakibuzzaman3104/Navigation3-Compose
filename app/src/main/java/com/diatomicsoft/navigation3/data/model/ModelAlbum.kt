package com.diatomicsoft.navigation3.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "albums")
@JsonClass(generateAdapter = true)
data class ModelAlbum(
    @PrimaryKey
    @param:Json(name = "id")
    val id: Int,
    @param:Json(name = "title")
    val title: String,
    @param:Json(name = "userId")
    val userId: Int
)