package com.diatomicsoft.core.database.base

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diatomicsoft.core.database.constants.DatabaseConstants
import com.diatomicsoft.core.database.converter.Converters
import com.diatomicsoft.core.database.entity.*
import com.diatomicsoft.core.database.dao.*

@Database(
    entities = [
        ModelUser::class,
        ModelPost::class,
        ModelComment::class,
        ModelAlbum::class,
        ModelPhoto::class,
        ModelToDo::class,
    ],
    version = DatabaseConstants.DATABASE_VERSION,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class BaseDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
    abstract fun albumDao(): AlbumDao
    abstract fun photoDao(): PhotoDao
    abstract fun toDoDao(): ToDoDao
}
