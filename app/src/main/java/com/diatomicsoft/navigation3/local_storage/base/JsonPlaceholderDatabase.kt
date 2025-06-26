package com.diatomicsoft.navigation3.local_storage.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.diatomicsoft.navigation3.constants.DatabaseConstants
import com.diatomicsoft.navigation3.data.model.ModelAlbum
import com.diatomicsoft.navigation3.data.model.ModelComment
import com.diatomicsoft.navigation3.data.model.ModelPhoto
import com.diatomicsoft.navigation3.data.model.ModelPost
import com.diatomicsoft.navigation3.data.model.ModelToDo
import com.diatomicsoft.navigation3.data.model.ModelUser
import com.diatomicsoft.navigation3.local_storage.converter.AlbumTypeConverter
import com.diatomicsoft.navigation3.local_storage.converter.CommentTypeConverter
import com.diatomicsoft.navigation3.local_storage.converter.PhotoTypeConverter
import com.diatomicsoft.navigation3.local_storage.converter.PostTypeConverter
import com.diatomicsoft.navigation3.local_storage.converter.ToDoTypeConverter
import com.diatomicsoft.navigation3.local_storage.converter.UserTypeConverter
import com.diatomicsoft.navigation3.local_storage.dao.UserDao
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        ModelPost::class,
        ModelComment::class,
        ModelUser::class,
        ModelAlbum::class,
        ModelPhoto::class,
        ModelToDo::class,
    ],
    version = DatabaseConstants.DATABASE_VERSION,
    exportSchema = false,
)
@TypeConverters(
    AlbumTypeConverter::class, CommentTypeConverter::class, PhotoTypeConverter::class,
    PostTypeConverter::class, ToDoTypeConverter::class, UserTypeConverter::class
)
abstract class JsonPlaceholderDatabase() :
    RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        fun init(context: Context): JsonPlaceholderDatabase {
            return Room
                .databaseBuilder(
                    context,
                    JsonPlaceholderDatabase::class.java,
                    DatabaseConstants.DATABASE_NAME,
                ).build()
        }
    }

}


