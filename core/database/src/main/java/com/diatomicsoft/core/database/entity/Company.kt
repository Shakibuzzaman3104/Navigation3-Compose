package com.diatomicsoft.core.database.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class Company(
    @param:Json(name = "bs")
    val bs: String,
    @param:Json(name = "catchPhrase")
    val catchPhrase: String,
    @param:Json(name = "name")
    val name: String
)