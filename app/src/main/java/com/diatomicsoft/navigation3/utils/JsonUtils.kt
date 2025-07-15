package com.diatomicsoft.navigation3.utils

import android.content.Context
import android.util.Log
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException

fun Context.loadJsonFromAssets(fileName: String): String? {
    return try {
        // Open the file from assets
        val inputStream = assets.open(fileName)
        // Read the content using InputStreamReader
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charsets.UTF_8)
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        null
    }
}

inline fun <reified T> Context.getDataListFromAssets(
    path: String,
    moshi: Moshi
): List<T> {
    val listType = Types.newParameterizedType(List::class.java, T::class.java)
    val adapter: JsonAdapter<List<T>> = moshi.adapter(listType)
    val jsonString = loadJsonFromAssets(path)

    return if (jsonString != null) {
        try {
            adapter.fromJson(jsonString) ?: emptyList()
        } catch (e: JsonDataException) {
            Log.e("AssetLoader", "Error parsing JSON with Moshi: ${e.message}", e)
            emptyList()
        } catch (e: IOException) {
            Log.e("AssetLoader", "IO Error reading or parsing JSON: ${e.message}", e)
            emptyList()
        }
    } else {
        Log.w("AssetLoader", "Failed to load $path from assets.")
        emptyList()
    }
}