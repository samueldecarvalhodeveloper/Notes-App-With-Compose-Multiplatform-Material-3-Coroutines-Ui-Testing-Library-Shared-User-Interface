package org.example.notesapp.unitaries.data.local_data_source.databases

import kotlinx.coroutines.test.runTest
import org.example.notesapp.mocks.UserDatabase
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class UserDatabaseTest {
    private lateinit var userDatabase: UserDatabase

    @BeforeTest
    fun beforeEach() {
        userDatabase = UserDatabase()
    }

    @Test
    fun testIfMethodGetDataAccessObjectReturnsUserDataAccessObjectImplementation() {
        runTest {
            val userDataAccessObject = userDatabase.getDataAccessObject()

            val usersFromDatabase = userDataAccessObject.getUsers()

            assertTrue(usersFromDatabase.isEmpty())
        }
    }
}