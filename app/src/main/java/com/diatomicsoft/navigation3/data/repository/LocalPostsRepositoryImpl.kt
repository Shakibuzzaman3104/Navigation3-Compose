package com.diatomicsoft.navigation3.data.repository

import android.content.Context
import com.diatomicsoft.navigation3.data.model.Post
import com.diatomicsoft.navigation3.domain.repository.PostsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException

class LocalPostsRepositoryImpl @Inject constructor(private val context: Context) :
    PostsRepository {


    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory()) // Must be added last
        .build()


    private val postListAdapter = moshi.adapter<List<Post>>(
        Types.newParameterizedType(List::class.java, Post::class.java)
    )

    private fun loadJsonFromAssets(fileName: String): String? {
        return try {
            // Open the file from assets
            val inputStream = context.assets.open(fileName)
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

    override suspend fun fetchPosts(): StateFlow<List<Post>> {
        delay(500)

        val jsonString = loadJsonFromAssets("posts.json")

        val _posts: MutableStateFlow<List<Post>> = MutableStateFlow<List<Post>>(emptyList())

        if (jsonString != null) {
            try {
                val actualPosts: List<Post>? = postListAdapter.fromJson(jsonString)
                _posts.value =
                    actualPosts ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error parsing JSON with Moshi: ${e.message}")
            }
        } else {
            println("Failed to load posts.json from assets.")
        }

        return _posts
    }

}