package org.example.notesapp.mocks

import org.example.notesapp.concerns.DatabaseQueriesFactory
import org.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject

class UserDatabase {
    fun getDataAccessObject(): UserDataAccessObject {
        val userQueries = DatabaseQueriesFactory.getUserQueriesInstance()

        return UserDataAccessObject(userQueries)
    }
}