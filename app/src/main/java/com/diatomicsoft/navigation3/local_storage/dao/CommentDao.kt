package com.diatomicsoft.navigation3.local_storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.diatomicsoft.navigation3.data.model.ModelComment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    @Query("SELECT * FROM comments")
    fun getAll(): Flow<List<ModelComment>>

    @Query("SELECT * FROM comments WHERE id = :id")
    fun getById(id: Int): Flow<ModelComment>

    @Query("SELECT * FROM comments WHERE postId = :id")
    fun getByPostId(id: Int): Flow<List<ModelComment>>

    @Insert
    suspend fun insert(comment: ModelComment)

    @Insert
    suspend fun insertAll(comments: List<ModelComment>)

    @Delete
    suspend fun delete(comment: ModelComment)

    @Query("DELETE FROM comments")
    suspend fun deleteAll()

    @Query("DELETE FROM comments WHERE id = :id")
    suspend fun deleteById(id: Int)


}