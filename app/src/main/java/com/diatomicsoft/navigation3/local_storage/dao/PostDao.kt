package com.diatomicsoft.navigation3.local_storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.diatomicsoft.navigation3.data.model.ModelPost
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    fun getAll(): Flow<List<ModelPost>>

    @Query("SELECT * FROM posts WHERE id = :id")
    fun getById(id: Int): Flow<ModelPost>

    @Query("SELECT * FROM posts WHERE userId = :id")
    fun getByUserId(id: Int): Flow<List<ModelPost>>

    @Insert
    suspend fun insert(post: ModelPost)

    @Insert
    suspend fun insertAll(posts: List<ModelPost>)

    @Delete
    suspend fun delete(post: ModelPost)

    @Query("DELETE FROM posts")
    suspend fun deleteAll()

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deleteById(id: Int)


}