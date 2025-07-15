package com.technonext.core.local_storage

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.technonext.core.local_storage.base.BaseDatabase
import com.technonext.core.local_storage.dao.UserDao
import com.technonext.core.local_storage.entity.UserEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaseDatabaseTest {
    private lateinit var database: BaseDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    BaseDatabase::class.java,
                ).allowMainThreadQueries()
                .build()

        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveUserData() =
        runBlocking {
            val user =
                UserEntity(
                    name = "Hasan",
                    img = "",
                    phone = "1234567890",
                    email = "",
                    gender = 1,
                    dob = "",
                    token = "",
                )
            userDao.insert(user)

            val retrievedUser =
                userDao.getUserByName("Hasan") // Use correct ID (Room starts at 1 for auto-generated IDs)
            assertThat(retrievedUser?.name, `is`("Hasan"))
        }
}
