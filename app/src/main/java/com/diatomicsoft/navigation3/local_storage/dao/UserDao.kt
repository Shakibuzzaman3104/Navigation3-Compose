package com.diatomicsoft.navigation3.local_storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diatomicsoft.navigation3.data.model.ModelUser
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<ModelUser>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: ModelUser)

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<ModelUser>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int): Flow<ModelUser?>

}