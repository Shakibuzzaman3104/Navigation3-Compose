package com.diatomicsoft.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.diatomicsoft.core.database.entity.ModelUser
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

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUser(id: Int)

}