package com.diatomicsoft.navigation3.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "comments")
@JsonClass(generateAdapter = true)
data class ModelComment(
    @param:Json(name = "body")
    val body: String,
    @param:Json(name = "email")
    val email: String,
    @PrimaryKey
    @param:Json(name = "id")
    val id: Int,
    @param:Json(name = "name")
    val name: String,
    @param:Json(name = "postId")
    val postId: Int
)