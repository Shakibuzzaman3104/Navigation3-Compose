package com.diatomicsoft.navigation3.local_storage.converter

import androidx.room.TypeConverter
import com.diatomicsoft.navigation3.data.model.ModelPost
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

class PostTypeConverter : BaseTypeConverter() {

    @TypeConverter
    fun fromString(value: String): ModelPost? {
        val jsonAdapter: JsonAdapter<ModelPost> =
            moshi.adapter(ModelPost::class.java)
        return jsonAdapter.fromJson(value)
    }

    @TypeConverter
    fun toJson(type: ModelPost): String {
        val jsonAdapter: JsonAdapter<ModelPost> =
            moshi.adapter(ModelPost::class.java)
        return jsonAdapter.toJson(type)
    }

}
