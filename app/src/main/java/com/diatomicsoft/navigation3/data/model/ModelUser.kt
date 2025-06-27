package com.diatomicsoft.navigation3.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "users")
@JsonClass(generateAdapter = true)
data class ModelUser(
    @param:Json(name = "address")
    val address: Address,
    @param:Json(name = "company")
    val company: Company,
    @param:Json(name = "email")
    val email: String,
    @PrimaryKey
    @param:Json(name = "id")
    val id: Int,
    @param:Json(name = "name")
    val name: String,
    @param:Json(name = "phone")
    val phone: String,
    @param:Json(name = "username")
    val username: String,
    @param:Json(name = "website")
    val website: String
)