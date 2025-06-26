package com.diatomicsoft.navigation3.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class Address(
    @param:Json(name = "city")
    val city: String,
    @param:Json(name = "geo")
    val geo: Geo,
    @param:Json(name = "street")
    val street: String,
    @param:Json(name = "suite")
    val suite: String,
    @param:Json(name = "zipcode")
    val zipcode: String
)