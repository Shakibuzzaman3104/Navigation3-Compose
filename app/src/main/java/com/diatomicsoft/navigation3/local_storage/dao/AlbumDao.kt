package com.diatomicsoft.navigation3.local_storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.diatomicsoft.navigation3.data.model.ModelAlbum
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums")
    fun getAll(): Flow<List<ModelAlbum>>

    @Query("SELECT * FROM albums WHERE id = :id")
    fun getById(id: Int): Flow<ModelAlbum>

    @Query("SELECT * FROM albums WHERE userId = :id")
    fun getByUserId(id: Int): Flow<List<ModelAlbum>>

    @Insert
    suspend fun insert(album: ModelAlbum)

    @Insert
    suspend fun insertAll(albums: List<ModelAlbum>)

    @Delete
    suspend fun delete(album: ModelAlbum)

    @Query("DELETE FROM albums")
    suspend fun deleteAll()

    @Query("DELETE FROM albums WHERE id = :id")
    suspend fun deleteById(id: Int)

}