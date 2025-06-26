package com.diatomicsoft.navigation3.local_storage.converter

import androidx.room.TypeConverter
import com.diatomicsoft.navigation3.data.model.ModelAlbum
import com.squareup.moshi.JsonAdapter

class AlbumTypeConverter : BaseTypeConverter() {
    @TypeConverter
    fun fromString(value: String): ModelAlbum? {
        val jsonAdapter: JsonAdapter<ModelAlbum> =
            moshi.adapter(ModelAlbum::class.java)
        return jsonAdapter.fromJson(value)
    }

    @TypeConverter
    fun toJson(type: ModelAlbum): String {
        val jsonAdapter: JsonAdapter<ModelAlbum> =
            moshi.adapter(ModelAlbum::class.java)
        return jsonAdapter.toJson(type)
    }
}