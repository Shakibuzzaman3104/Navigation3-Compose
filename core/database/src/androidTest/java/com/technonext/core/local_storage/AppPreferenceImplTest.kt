package com.technonext.core.local_storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.technonext.core.local_storage.pref.AppPreferenceImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppPreferenceImplInstrumentedTest {
    private lateinit var context: Context
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var appPreference: AppPreferenceImpl

    @Before
    fun setUp() {
        // Use the application context for creating the DataStore
        context = ApplicationProvider.getApplicationContext<Context>()

        // Create a temporary file for the DataStore in the instrumented environment
        val testFile = File(context.filesDir, "test_datastore.preferences_pb")
        if (testFile.exists()) {
            testFile.delete()
        }

        dataStore =
            PreferenceDataStoreFactory.create(
                corruptionHandler = null,
                migrations = emptyList(),
                scope = CoroutineScope(Dispatchers.IO),
            ) {
                testFile
            }

        appPreference = AppPreferenceImpl(dataStore)
    }

    @After
    fun tearDown() {
        // Clean up the test DataStore file
        val testFile = File(context.filesDir, "test_datastore.preferences_pb")
        if (testFile.exists()) {
            testFile.delete()
        }
    }

    @Test
    fun testSaveAndRetrieveString() =
        runBlocking {
            val stringKey = stringPreferencesKey("string_key")
            val testValue = "Hello, DataStore!"

            // Save the string value
            appPreference.saveValue(stringKey, testValue)

            // Retrieve the string value
            val result = appPreference.getValue(stringKey, "Default Value").first()

            // Verify the result
            assertEquals(testValue, result)
        }

    @Test
    fun testRetrieveDefaultStringWhenKeyDoesNotExist() =
        runBlocking {
            val stringKey = stringPreferencesKey("nonexistent_key")
            val defaultValue = "Default Value"

            // Retrieve the value
            val result = appPreference.getValue(stringKey, defaultValue).first()

            // Verify the result
            assertEquals(defaultValue, result)
        }
}
