package org.example.notesapp.data.local_data_source.databases

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.example.notesapp.Database
import org.example.notesapp.constants.data.DomainAgnosticConstants.DATABASE_FILE_NAME
import org.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class UserDatabase {
    actual fun getInstance(): UserDataAccessObject {
        val databaseDriverImplementation: SqlDriver =
            NativeSqliteDriver(Database.Schema, DATABASE_FILE_NAME)

        val databaseImplementation = Database(databaseDriverImplementation)

        return UserDataAccessObject(databaseImplementation.userQueries)
    }
}