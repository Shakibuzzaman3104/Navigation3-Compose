package com.diatomicsoft.navigation3.local_storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.diatomicsoft.navigation3.data.model.ModelPhoto
import kotlinx.coroutines.flow.Flow


@Dao
interface PhotoDao {

    @Query("SELECT * FROM photos")
    fun getAll(): Flow<List<ModelPhoto>>

    @Query("SELECT * FROM photos WHERE id = :id")
    fun getById(id: Int): Flow<ModelPhoto>

    @Query("SELECT * FROM photos WHERE id = :id")
    fun getByAlbumId(id: Int): Flow<List<ModelPhoto>>

    @Insert
    suspend fun insert(photo: ModelPhoto)

    @Insert
    suspend fun insertAll(photos: List<ModelPhoto>)

    @Delete
    suspend fun delete(photo: ModelPhoto)

    @Query("DELETE FROM photos")
    suspend fun deleteAll()

    @Query("DELETE FROM photos WHERE id = :id")
    suspend fun deleteById(id: Int)

}