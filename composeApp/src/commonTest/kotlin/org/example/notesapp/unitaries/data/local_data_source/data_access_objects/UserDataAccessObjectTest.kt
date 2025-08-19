package org.example.notesapp.unitaries.data.local_data_source.data_access_objects

import kotlinx.coroutines.test.runTest
import org.example.notesapp.concerns.DatabaseQueriesFactory
import org.example.notesapp.constants.data.UserConstants.USER_ENTITY_OBJECT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject
import orgexamplenotesappdatalocaldatabase.UserQueries
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserDataAccessObjectTest {
    private lateinit var userDataAccessObject: UserDataAccessObject
    private lateinit var userQueries: UserQueries

    @BeforeTest
    fun beforeEach() {
        userQueries = DatabaseQueriesFactory.getUserQueriesInstance()

        userDataAccessObject = UserDataAccessObject(userQueries)
    }

    @Test
    fun testIfMethodGetUsersReturnsAllUsersFromDatabase() {
        runTest {
            val usersFromDatabase = userDataAccessObject.getUsers()

            assertTrue(usersFromDatabase.isEmpty())
        }
    }

    @Test
    fun testIfMethodCreateUserReturnsCreatesUserOnDatabase() {
        runTest {
            userDataAccessObject.createUser(USER_ENTITY_OBJECT)

            val usersFromDatabase = userQueries.getUsers().executeAsList()

            assertEquals(USER_ID, usersFromDatabase.first().id.toInt())
            assertEquals(USER_USERNAME, usersFromDatabase.first().username)
        }
    }
}