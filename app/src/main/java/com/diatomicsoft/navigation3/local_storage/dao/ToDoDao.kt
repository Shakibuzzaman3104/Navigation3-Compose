package com.diatomicsoft.navigation3.local_storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.diatomicsoft.navigation3.data.model.ModelToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<ModelToDo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getById(id: Int): Flow<ModelToDo>

    @Query("SELECT * FROM todo WHERE userId = :id")
    fun getByUserId(id: Int): Flow<List<ModelToDo>>

    @Insert
    suspend fun insert(todo: ModelToDo)

    @Insert
    suspend fun insertAll(todos: List<ModelToDo>)

    @Delete
    suspend fun delete(todo: ModelToDo)

    @Query("DELETE FROM todo")
    suspend fun deleteAll()

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteById(id: Int)


}