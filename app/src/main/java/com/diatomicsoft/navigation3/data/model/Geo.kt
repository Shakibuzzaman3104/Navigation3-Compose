package com.diatomicsoft.navigation3.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class Geo(
    @param:Json(name = "lat")
    val lat: String,
    @param:Json(name = "lng")
    val lng: String
)