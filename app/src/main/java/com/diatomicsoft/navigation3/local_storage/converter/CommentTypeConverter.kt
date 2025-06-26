package com.diatomicsoft.navigation3.local_storage.converter

import androidx.room.TypeConverter
import com.diatomicsoft.navigation3.data.model.ModelComment
import com.diatomicsoft.navigation3.data.model.ModelToDo
import com.squareup.moshi.JsonAdapter

class CommentTypeConverter : BaseTypeConverter() {
    @TypeConverter
    fun fromString(value: String): ModelComment? {
        val jsonAdapter: JsonAdapter<ModelComment> =
            moshi.adapter(ModelComment::class.java)
        return jsonAdapter.fromJson(value)
    }

    @TypeConverter
    fun toJson(type: ModelComment): String {
        val jsonAdapter: JsonAdapter<ModelComment> =
            moshi.adapter(ModelComment::class.java)
        return jsonAdapter.toJson(type)
    }
}