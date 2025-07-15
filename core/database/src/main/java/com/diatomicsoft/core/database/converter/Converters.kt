package com.diatomicsoft.core.database.converter

import androidx.room.TypeConverter
import com.diatomicsoft.core.database.entity.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters {
    
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Address converter
    @TypeConverter
    fun fromAddressString(value: String?): Address? {
        return value?.let {
            val adapter: JsonAdapter<Address> = moshi.adapter(Address::class.java)
            adapter.fromJson(it)
        }
    }

    @TypeConverter
    fun addressToString(address: Address?): String? {
        return address?.let {
            val adapter: JsonAdapter<Address> = moshi.adapter(Address::class.java)
            adapter.toJson(it)
        }
    }

    // Company converter
    @TypeConverter
    fun fromCompanyString(value: String?): Company? {
        return value?.let {
            val adapter: JsonAdapter<Company> = moshi.adapter(Company::class.java)
            adapter.fromJson(it)
        }
    }

    @TypeConverter
    fun companyToString(company: Company?): String? {
        return company?.let {
            val adapter: JsonAdapter<Company> = moshi.adapter(Company::class.java)
            adapter.toJson(it)
        }
    }

    // Geo converter
    @TypeConverter
    fun fromGeoString(value: String?): Geo? {
        return value?.let {
            val adapter: JsonAdapter<Geo> = moshi.adapter(Geo::class.java)
            adapter.fromJson(it)
        }
    }

    @TypeConverter
    fun geoToString(geo: Geo?): String? {
        return geo?.let {
            val adapter: JsonAdapter<Geo> = moshi.adapter(Geo::class.java)
            adapter.toJson(it)
        }
    }

    // ModelPost converter
    @TypeConverter
    fun fromPostString(value: String?): ModelPost? {
        return value?.let {
            val adapter: JsonAdapter<ModelPost> = moshi.adapter(ModelPost::class.java)
            adapter.fromJson(it)
        }
    }

    @TypeConverter
    fun postToString(post: ModelPost?): String? {
        return post?.let {
            val adapter: JsonAdapter<ModelPost> = moshi.adapter(ModelPost::class.java)
            adapter.toJson(it)
        }
    }

    // ModelAlbum converter
    @TypeConverter
    fun fromAlbumString(value: String?): ModelAlbum? {
        return value?.let {
            val adapter: JsonAdapter<ModelAlbum> = moshi.adapter(ModelAlbum::class.java)
            adapter.fromJson(it)
        }
    }

    @TypeConverter
    fun albumToString(album: ModelAlbum?): String? {
        return album?.let {
            val adapter: JsonAdapter<ModelAlbum> = moshi.adapter(ModelAlbum::class.java)
            adapter.toJson(it)
        }
    }

    // ModelComment converter
    @TypeConverter
    fun fromCommentString(value: String?): ModelComment? {
        return value?.let {
            val adapter: JsonAdapter<ModelComment> = moshi.adapter(ModelComment::class.java)
            adapter.fromJson(it)
        }
    }

    @TypeConverter
    fun commentToString(comment: ModelComment?): String? {
        return comment?.let {
            val adapter: JsonAdapter<ModelComment> = moshi.adapter(ModelComment::class.java)
            adapter.toJson(it)
        }
    }

    // ModelPhoto converter
    @TypeConverter
    fun fromPhotoString(value: String?): ModelPhoto? {
        return value?.let {
            val adapter: JsonAdapter<ModelPhoto> = moshi.adapter(ModelPhoto::class.java)
            adapter.fromJson(it)
        }
    }

    @TypeConverter
    fun photoToString(photo: ModelPhoto?): String? {
        return photo?.let {
            val adapter: JsonAdapter<ModelPhoto> = moshi.adapter(ModelPhoto::class.java)
            adapter.toJson(it)
        }
    }

    // ModelToDo converter
    @TypeConverter
    fun fromToDoString(value: String?): ModelToDo? {
        return value?.let {
            val adapter: JsonAdapter<ModelToDo> = moshi.adapter(ModelToDo::class.java)
            adapter.fromJson(it)
        }
    }

    @TypeConverter
    fun toDoToString(toDo: ModelToDo?): String? {
        return toDo?.let {
            val adapter: JsonAdapter<ModelToDo> = moshi.adapter(ModelToDo::class.java)
            adapter.toJson(it)
        }
    }
}
