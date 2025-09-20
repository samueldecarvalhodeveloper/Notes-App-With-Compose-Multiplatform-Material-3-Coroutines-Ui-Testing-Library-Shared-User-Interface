package org.example.notesapp.data.local_data_source.databases

import org.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class UserDatabase {
    fun getInstance(): UserDataAccessObject
}