package com.diatomicsoft.navigation3.local_storage.converter

import androidx.room.TypeConverter
import com.diatomicsoft.navigation3.data.model.ModelToDo
import com.squareup.moshi.JsonAdapter

class ToDoTypeConverter : BaseTypeConverter() {
    @TypeConverter
    fun fromString(value: String): ModelToDo? {
        val jsonAdapter: JsonAdapter<ModelToDo> =
            moshi.adapter(ModelToDo::class.java)
        return jsonAdapter.fromJson(value)
    }

    @TypeConverter
    fun toJson(type: ModelToDo): String {
        val jsonAdapter: JsonAdapter<ModelToDo> =
            moshi.adapter(ModelToDo::class.java)
        return jsonAdapter.toJson(type)
    }
}