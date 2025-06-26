package com.diatomicsoft.navigation3.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "todo")
@JsonClass(generateAdapter = true)
data class ModelToDo(
    @param:Json(name = "completed")
    val completed: Boolean,
    @PrimaryKey
    @param:Json(name = "id")
    val id: Int,
    @param:Json(name = "title")
    val title: String,
    @param:Json(name = "userId")
    val userId: Int
)