package com.diatomicsoft.navigation3.local_storage.converter

import androidx.room.TypeConverter
import com.diatomicsoft.navigation3.data.model.ModelPhoto
import com.squareup.moshi.JsonAdapter

class PhotoTypeConverter : BaseTypeConverter() {
    @TypeConverter
    fun fromString(value: String): ModelPhoto? {
        val jsonAdapter: JsonAdapter<ModelPhoto> =
            moshi.adapter(ModelPhoto::class.java)
        return jsonAdapter.fromJson(value)
    }

    @TypeConverter
    fun toJson(type: ModelPhoto): String {
        val jsonAdapter: JsonAdapter<ModelPhoto> =
            moshi.adapter(ModelPhoto::class.java)
        return jsonAdapter.toJson(type)
    }
}